
package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Template(
    @Expose
    @SerializedName("TemplateID") val templateID: Int,
    @Expose
    @SerializedName("TaskID") val taskID: Int,
    @Expose
    @SerializedName("UserID") val userID: Int,
    @Expose
    @SerializedName("TodoList") val todoList: Task
): Parcelable {}

data class ListTemplate(
    val templete: List<Template>
)