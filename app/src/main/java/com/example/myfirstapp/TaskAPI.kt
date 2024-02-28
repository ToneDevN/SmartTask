package com.example.myfirstapp

import com.example.myfirstapp.DataClass.Category
import com.example.myfirstapp.DataClass.ListCategory
import com.example.myfirstapp.DataClass.ListRoutine
import com.example.myfirstapp.DataClass.ListTask
import com.example.myfirstapp.DataClass.ListTemplate
import com.example.myfirstapp.DataClass.LoginClass
import com.example.myfirstapp.DataClass.Routine
import com.example.myfirstapp.DataClass.SigninRequest
import com.example.myfirstapp.DataClass.SignupRequest
import com.example.myfirstapp.DataClass.SubtaskList
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.Template
import com.example.myfirstapp.DataClass.TodoListRequest
import com.example.myfirstapp.DataClass.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface TaskAPI {

    @POST("/api/auth/signin")
    fun signin(
        @Body request: SigninRequest
    ): Call<LoginClass>

    @POST("/api/auth/signup")
    fun signup(
        @Body request: SignupRequest
    ): Call<LoginClass>


    @POST("/api/task/create")
    fun createTask(
        @Header("Authorization") authorization: String,
        @Body request: TodoListRequest
    ): Call<Task>


    @Multipart
    @PUT("/api/task/update")
    fun updateTask(
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("URL") url: RequestBody,
        @Part("Location") location: RequestBody,
        @Part("Priority") priority: RequestBody,
        @Part("Date") date: RequestBody,
        @Part("Time") time: RequestBody,
        @Part("CategoryID") categoryID: RequestBody,
        @Part("Subtasks") subtask: RequestBody
    ): Call<Task>

    @Multipart
    @PUT("/api/task/delete")
    fun deleteTask(
        @Part("TaskID") taskID: RequestBody
    )

    @Multipart
    @GET("/api/task/task")
    fun getTask(
        @Header("Authorization") authorization: String,
        @Part("TaskID") taskID: RequestBody
    ): Call<Task>

    @GET("/api/task/listTask")
    fun getListTask(
        @Header("Authorization") authorization: String
    ): Call<ListTask>


    @Multipart
    @GET("/api/task/listTaskbyDate")
    fun getListTaskByDate(
        @Part("Date") date: RequestBody
    ): Call<ListTask>

    @Multipart
    @GET("/api/task/weekhastask")
    fun getWeekHasTask(
        @Part("Date") date: RequestBody
    ): Call<ListTask>
    @Multipart
    @PUT("/api/task/completed")
    fun TaskCompletd(
        @Part("TaskID") taskID: RequestBody
    )
    @Multipart
    @PUT("/api/user/upload/image")
    fun uploadImage(
        @Part image: MultipartBody.Part,
    )

    @Multipart
    @PUT("/api/user/update")
    fun uploadUser(
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("email") email: RequestBody,
    ): Call<User>

    @Multipart
    @PUT("/api/user/delete")
    fun deleteUser()

    @Multipart
    @POST("/api/category/create")
    fun createCategory(
        @Part("Category") Category: RequestBody,
    ): Call<Category>

    @Multipart
    @PUT("/api/category/update")
    fun updateCategory(
        @Part("CategoryID") CategoryID: RequestBody,
        @Part("Category") Category: RequestBody,
    ): Call<Category>

    @Multipart
    @PUT("/api/category/delete")
    fun deleteCategory(
        @Part("CategoryID") CategoryID: RequestBody,
    )

    @Multipart
    @GET("/api/category/task")
    fun getCategoryHasTask(
        @Part("Date") date: RequestBody
    ): Call<ListCategory>

    @Multipart
    @GET("/api/category/listCategory")
    fun getCategory(): Call<ListCategory>

    @Multipart
    @POST("/api/subtask/create")
    fun createSubtask(
        @Part("TaskID") taskID: RequestBody,
        @Part("Title") title: RequestBody
    ): Call<Task>

    @Multipart
    @PUT("/api/subtask/update")
    fun updateSubtask (
        @Part("TaskID") taskID: RequestBody,
        @Part("SubtaskID") subtaskID: RequestBody,
        @Part("Title") title: RequestBody
    )

    @Multipart
    @PUT("/api/subtask/delete")
    fun deleteSubtask (
        @Part("TaskID") taskID: RequestBody,
        @Part("SubtaskID") subtaskID: RequestBody,
    )

    @Multipart
    @GET("/api/subtask/listSubTask")
    fun getSubtask (
        @Part("TaskID") taskID: RequestBody
    ): Call<SubtaskList>

    @Multipart
    @PUT("/api/subtask/completed")
    fun completeSubtask (
        @Part("TaskID") taskID: RequestBody,
        @Part("SubtaskID") subtaskID: RequestBody,
    )

    @Multipart
    @POST("/api/routine/create")
    fun createRoutine (
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("URL") url: RequestBody,
        @Part("Location") location: RequestBody,
        @Part("Priority") priority: RequestBody,
        @Part("Date") date: RequestBody,
        @Part("Time") time: RequestBody,
        @Part("CategoryID") categoryID: RequestBody,
        @Part("Subtasks") subtask: RequestBody,
        @Part("DateFrom") dateFrom: RequestBody,
        @Part("DateTo") dateTo: RequestBody
    )

    @Multipart
    @PUT("/api/routine/delete")
    fun deleteRoutine (
        @Part("RoutineID") routineID: RequestBody
    )

    @Multipart
    @PUT("/api/routine/update")
    fun updateRoutine (
        @Part("RoutineID") routineID: RequestBody,
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("URL") url: RequestBody,
        @Part("Location") location: RequestBody,
        @Part("Priority") priority: RequestBody,
        @Part("Date") date: RequestBody,
        @Part("Time") time: RequestBody,
        @Part("CategoryID") categoryID: RequestBody,
        @Part("Subtasks") subtask: RequestBody,
        @Part("DateFrom") dateFrom: RequestBody,
        @Part("DateTo") dateTo: RequestBody
    )

    @Multipart
    @GET("/api/routine/delete")
    fun getRoutineByID (
        @Part("RoutineID") routineID: RequestBody
    ):Call<Routine>

    @Multipart
    @GET("/api/routine/getlistRoutinebyUserID")
    fun getRoutineByUserID ():Call<ListRoutine>


    @Multipart
    @GET("/api/routine/getlistRoutinebyDate")
    fun getRoutineByDate ():Call<ListRoutine>

    @Multipart
    @PUT("/api/routine/completed")
    fun completedRoutine (
        @Part("RoutineID") routineID: RequestBody,
        @Part("TaskID") taskID: RequestBody,
    )

    @Multipart
    @POST("/api/template/create")
    fun createTemplate (
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("URL") url: RequestBody,
        @Part("Location") location: RequestBody,
        @Part("Priority") priority: RequestBody,
        @Part("Date") date: RequestBody,
        @Part("Time") time: RequestBody,
        @Part("CategoryID") categoryID: RequestBody,
        @Part("Subtasks") subtask: RequestBody
    )

    @Multipart
    @POST("/api/template/update")
    fun updateTemplate (
        @Part("TemplateID") templateID: RequestBody,
        @Part("Title") title: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("URL") url: RequestBody,
        @Part("Location") location: RequestBody,
        @Part("Priority") priority: RequestBody,
        @Part("Date") date: RequestBody,
        @Part("Time") time: RequestBody,
        @Part("CategoryID") categoryID: RequestBody,
        @Part("Subtasks") subtask: RequestBody
    )

    @Multipart
    @PUT("/api/template/delete")
    fun deleteTemplate (
        @Part("TemplateID") templateID: RequestBody
    )

    @Multipart
    @PUT("/api/template/getListTemplate")
    fun getListTemplate (
        @Part("TemplateID") templateID: RequestBody
    ): Call<ListTemplate>

    @Multipart
    @PUT("/api/template/getTemplate")
    fun getTemplate (
        @Part("TemplateID") templateID: RequestBody
    ): Call<Template>


    companion object {
        fun create(): TaskAPI {
            val taskClient : TaskAPI = Retrofit.Builder()
                .baseUrl("http://10.199.120.62:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskAPI ::class.java)
            return taskClient
        }
    }

}