package com.example.myfirstapp.DataClass

import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.http.Part

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

data class UpdateTodoListRequest(
    val TaskID: Int,
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

data class TaskIDRequest(
    val taskID: Int
)

data class DateRequest(
    val Date: String
)

data class UpdateUserRequest(
     val firstName: String,
     val lastName: String,
     val email: String,
)

data class CreateCategory(
    val Category: String
)

data class UpdateCategory(
    val CategoryID: Int,
    val Category: String,
)

data class CategoryIDRequest(
    val CategoryID: Int
)

data class CreateSubtask(
    val TaskID: Int,
    val Title: String,
)

data class UpdateSubtask(
    val TaskID: Int,
    val SubtaskID: Int,
    val Title: String,
)

data class DeleteSubtask(
    val TaskID: Int,
    val SubtaskID: Int,
)

data class CreateRoutine(
    val Title: String,
    val Description: String,
    val URL: String,
    val Location: String,
    val Priority: Int,
    val Date: String,
    val Time: String,
    val CategoryID: Int? = null,
    val Subtasks: List<String>? = null,
    val DateFrom: String,
    val DateTo: String,
)

data class DeleteRoutine(
    val RoutineID: Int
)

data class UpdateRoutine(
    val RoutineID: Int,
    val Title: String,
    val Description: String,
    val URL: String,
    val Location: String,
    val Priority: Int,
    val Date: String,
    val Time: String,
    val CategoryID: Int? = null,
    val Subtasks: List<String>? = null,
    val DateFrom: String,
    val DateTo: String
)

data class CompletedRoutine(
    val RoutineID: Int,
    val TaskID: Int
)

data class CreateTemplate(
    val Title: String,
    val Description: String,
    val URL: String,
    val Location: String,
    val Priority: Int,
    val Date: String,
    val Time: String,
    val CategoryID: Int? = null,
    val Subtasks: List<String>? = null,
)

data class UpdateTemplate(
    val TemplateID: Int,
    val Title: String,
    val Description: String,
    val URL: String,
    val Location: String,
    val Priority: Int,
    val Date: String,
    val Time: String,
    val CategoryID: Int? = null,
    val Subtasks: List<String>? = null,
)

data class TemplateIDRequest(
    val TemplateID: Int
)
