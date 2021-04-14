package com.example.androidtest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidtest.data.entities.User
import com.example.androidtest.data.repository.LoginRepository
import com.example.androidtest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    var userName = ""
    var password = ""

    var userNameError = MutableLiveData<String>()
    var passwordError = MutableLiveData<String>()

    fun login() = liveData {
        if(userName.isEmpty()) {
            userNameError.value = "Username is not empty"
            emit(Resource.error(userNameError.value!!))
            return@liveData
        }
        if(!userName.matches("[A-Za-z0-9]*".toRegex())
            || userName.length > 30) {
            userNameError.value = "Username is not valid (Characters and Numbers (max 30))"
            emit(Resource.error(userNameError.value!!))
            return@liveData
        }
        if(password.isEmpty()) {
            passwordError.value = "Password is not empty"
            emit(Resource.error(passwordError.value!!))
            return@liveData
        }
        if(!password.matches("[A-Za-z0-9]*".toRegex())
            || userName.length > 30) {
            passwordError.value = "Password is not valid (Characters and Numbers (max 16))"
            emit(Resource.error(passwordError.value!!))
            return@liveData
        }
        emitSource(loginRepository.login(userName, password))
    }

    val currentUser: LiveData<User?>
            get() = loginRepository.currentUser
}