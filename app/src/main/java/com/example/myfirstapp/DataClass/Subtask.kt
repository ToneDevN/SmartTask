package com.example.myfirstapp.DataClass

data class Subtask(
    val Completed: Boolean,
    val DeletedAt: String,
    val SubtaskID: Int,
    val TaskID: Int,
    val TitleSubTask: String,
    val UserID: Int,
    val createdAt: String,
    val updatedAt: String
)