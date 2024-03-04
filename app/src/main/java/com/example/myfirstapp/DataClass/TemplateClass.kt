
package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TodoResponse(
    @SerializedName("TemplateID")
    val templateId: Int,
    @SerializedName("TaskID")
    val taskId: Int,
    @SerializedName("UserID")
    val userId: Int,
    @SerializedName("TodoList")
    val todoList: TaskData
)

data class TaskData(
    @SerializedName("Title")
    val title: String
)

data class ListTemplate(
    val templete: List<TodoResponse>
)