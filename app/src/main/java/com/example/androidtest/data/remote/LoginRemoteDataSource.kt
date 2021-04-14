package com.example.androidtest.data.remote

import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginService: LoginService
) : BaseDataSource() {
    suspend fun login(data: Map<String, String>) = getResult { loginService.login(data) }
}