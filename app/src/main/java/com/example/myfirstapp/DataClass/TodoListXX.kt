package com.example.myfirstapp.DataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoListXX(
    val CategoryID: Int,
    val Completed: Boolean,
    val Date: String,
    val DeletedAt: String,
    val Description: String,
    val Location: String,
    val Priority: Int,
    val RoutineID: Int,
    val Subtask: List<Subtask>,
    val TaskID: Int,
    val Time: String,
    val Title: String,
    val URL: String,
    val UserID: Int,
    val createdAt: String,
    val updatedAt: String
): Parcelable