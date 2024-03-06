package com.example.myfirstapp.DataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskXX(
    val DeletedAt: String,
    val TaskID: Int,
    val TemplateID: Int,
    val TodoList: TodoListXX,
    val UserID: Int,
    val createdAt: String,
    val updatedAt: String
): Parcelable