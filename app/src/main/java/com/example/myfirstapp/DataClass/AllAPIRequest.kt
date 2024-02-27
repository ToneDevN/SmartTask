package com.example.myfirstapp.DataClass

data class SigninRequest(
    val email: String,
    val password: String
)

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class TodoListRequest(
    val Title: String,
    val Description: String,
    val URL: String,
    val Location: String,
    val Priority: Int,
    val Date: String,
    val Time: String,
    val Subtasks: List<String>? = null,
    val CategoryID: Int? = null
)

