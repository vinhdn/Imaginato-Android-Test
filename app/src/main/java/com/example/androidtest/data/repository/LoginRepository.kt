package com.example.androidtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.androidtest.data.entities.User
import com.example.androidtest.data.entities.response.LoginResponse
import com.example.androidtest.data.local.UserDao
import com.example.androidtest.data.remote.LoginRemoteDataSource
import com.example.androidtest.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: UserDao
) {
    fun login(userName: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        localDataSource.clear()
        val responseStatus = remoteDataSource.login(mapOf("username" to userName, "password" to password))
        if (responseStatus.status == Resource.Status.SUCCESS) {
            val user = responseStatus.data?.user
            if(user != null) {
                user.xAcc = responseStatus.header?.get("X-Acc")
                localDataSource.insert(user)
                emit(responseStatus)
            } else {
                emit(Resource(Resource.Status.ERROR, null, responseStatus.message!!))
            }
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource(Resource.Status.ERROR, null, responseStatus.message!!))
        }
    }

    val currentUser: LiveData<User?> get() = localDataSource.getCurrentUser()
}