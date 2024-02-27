package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @Expose
    @SerializedName("CategoryID") val categoryID: Int,
    @Expose
    @SerializedName("Category") val category: String,
): Parcelable {}

@Parcelize
data class TaskInCategory(
    @Expose
    @SerializedName("CategoryID") val categoryID: Int,
    @Expose
    @SerializedName("Category") val category: String,
    @Expose
    @SerializedName("createdAt") val createdAt: String,
    @Expose
    @SerializedName("updatedAt") val updatedAt: String,
    @Expose
    @SerializedName("DeletedAt") val deletedAt: String?,
    @Expose
    @SerializedName("UserID") val userID: Int,
    @Expose
    @SerializedName("Todolist") val todoList: List<Task> // หรือถ้ามี data class สำหรับ Todolist สามารถใช้ data class นั้นแทน List<Any>
): Parcelable {}

@Parcelize
data class ListCategory(
    @Expose
    @SerializedName("category") val categories: List<Category>
): Parcelable {}