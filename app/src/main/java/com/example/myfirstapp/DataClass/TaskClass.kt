package com.example.myfirstapp.DataClass

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serial

@Parcelize
data class Task(
    @Expose
    @SerializedName("TaskID") val taskID: Int,
    @Expose
    @SerializedName("Title") val title: String,
    @Expose
    @SerializedName("Description") val description: String,
    @Expose
    @SerializedName("URL") val url: String?,
    @Expose
    @SerializedName("Location") val location: String?,
    @Expose
    @SerializedName("Priority") val priority: Int,
    @Expose
    @SerializedName("Date") val date: String?,
    @Expose
    @SerializedName("Time") val time: String?,
    @Expose
    @SerializedName("Completed") val completed: Boolean,
    @Expose
    @SerializedName("Subtask") val subtasks: List<Subtask>?,
    @Expose
    @SerializedName("Categories") val categories: List<Category>?,
    @Expose
    @SerializedName("CategoryID") val categoryID: Int?,
    @Expose
    @SerializedName("RoutineID") val routineID: Int?,
): Parcelable {}

@Parcelize
data class ListTask(
    @Expose
    @SerializedName("tasks") val tasks: List<Task>
) : Parcelable {}


