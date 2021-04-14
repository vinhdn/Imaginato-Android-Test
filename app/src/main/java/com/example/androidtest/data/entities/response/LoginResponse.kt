package com.example.androidtest.data.entities.response

import com.example.androidtest.data.entities.User

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String? = null,
    val user: User? = null
)