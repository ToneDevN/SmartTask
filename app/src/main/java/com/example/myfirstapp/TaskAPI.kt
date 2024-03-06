package com.example.myfirstapp

import com.example.myfirstapp.DataClass.Category
import com.example.myfirstapp.DataClass.CategoryIDRequest
import com.example.myfirstapp.DataClass.CompletedRoutine
import com.example.myfirstapp.DataClass.CreateCategory
import com.example.myfirstapp.DataClass.CreateRoutine
import com.example.myfirstapp.DataClass.CreateSubtask
import com.example.myfirstapp.DataClass.CreateTemplate
import com.example.myfirstapp.DataClass.DeleteRoutine
import com.example.myfirstapp.DataClass.DeleteSubtask
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
import com.example.myfirstapp.DataClass.TaskByTemplateDate
import com.example.myfirstapp.DataClass.TaskIDRequest
import com.example.myfirstapp.DataClass.TemplateData
import com.example.myfirstapp.DataClass.TemplateDate
import com.example.myfirstapp.DataClass.TemplateIDRequest
import com.example.myfirstapp.DataClass.TodoListRequest
import com.example.myfirstapp.DataClass.UpdateCategory
import com.example.myfirstapp.DataClass.UpdateRoutine
import com.example.myfirstapp.DataClass.UpdateSubtask
import com.example.myfirstapp.DataClass.UpdateTemplate
import com.example.myfirstapp.DataClass.UpdateTodoListRequest
import com.example.myfirstapp.DataClass.UpdateUserRequest
import com.example.myfirstapp.DataClass.User
import com.example.myfirstapp.DataClass.completedTask
import com.example.myfirstapp.DataClass.weekTaskX
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
import retrofit2.http.Path

interface TaskAPI {

    @POST("/api/auth/signin")
    fun signin(
        @Body request: SigninRequest
    ): Call<LoginClass>

    @POST("/api/auth/signup")
    fun signup(
        @Body request: SignupRequest
    ): Call<LoginClass>

    @GET("/api/auth/me")
    fun getUser(
        @Header("Authorization") authorization: String,
    ): Call<User>


    @POST("/api/task/create")
    fun createTask(
        @Header("Authorization") authorization: String,
        @Body request: TodoListRequest
    ): Call<Task>


    @PUT("/api/task/update")
    fun updateTask(
        @Header("Authorization") authorization: String,
        @Body request: UpdateTodoListRequest
    ): Call<Task>


    @PUT("/api/task/delete")
    fun deleteTask(
        @Header("Authorization") authorization: String,
        @Body request: TaskIDRequest
    )


    @GET("/api/task/task")
    fun getTask(
        @Header("Authorization") authorization: String,
        @Part("TaskID") taskID: RequestBody
    ): Call<Task>

    @GET("/api/task/listTask")
    fun getListTask(
        @Header("Authorization") authorization: String
    ): Call<ListTask>



    @GET("/api/task/listTaskbyDate/{date}")
    fun getListTaskByDate(
        @Header("Authorization") authorization: String,
        @Path("date") date: String,
    ): Call<ListTask>


    @GET("/api/task/weekhastask/{date}")
    fun getWeekHasTask(
        @Header("Authorization") authorization: String,
        @Path("date") date: String,
    ): Call<weekTaskX>

    @GET("/api/task/getListTaskByCategoryID/{id}")
    fun getListTaskByCategoryID(
        @Header("Authorization") authorization: String,
        @Path("id") CategoryID: String,
    ): Call<ListTask>


    @PUT("/api/task/completed")
    fun TaskCompletd(
        @Header("Authorization") authorization: String,
        @Body request: TaskIDRequest
    )
    @Multipart
    @PUT("/api/user/upload/image")
    fun uploadImage(
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part
    ): Call<Void>


    @PUT("/api/user/update")
    fun uploadUser(
        @Header("Authorization") authorization: String,
        @Body request: UpdateUserRequest
    ): Call<User>

    @PUT("/api/user/delete")
    fun deleteUser(@Header("Authorization") authorization: String)

    @POST("/api/category/create")
    fun createCategory(
        @Header("Authorization") authorization: String,
        @Body request: CreateCategory,
    ): Call<Category>

    @PUT("/api/category/update")
    fun updateCategory(
        @Header("Authorization") authorization: String,
        @Body request: UpdateCategory,
    ): Call<Category>

    @PUT("/api/category/delete")
    fun deleteCategory(
        @Header("Authorization") authorization: String,
        @Body request: CategoryIDRequest,
    )

    @GET("/api/category/task")
    fun getCategoryHasTask(
        @Header("Authorization") authorization: String,
        @Body request: CategoryIDRequest
    ): Call<ListCategory>


    @GET("/api/category/listCategory")
    fun getCategory( @Header("Authorization") authorization: String,): Call<ListCategory>


    @POST("/api/subtask/create")
    fun createSubtask(
        @Header("Authorization") authorization: String,
        @Body request: CreateSubtask
    ): Call<Task>

    @PUT("/api/subtask/update")
    fun updateSubtask(
        @Header("Authorization") authorization: String,
        @Body request: UpdateSubtask
    ): Call<Task>


    @PUT("/api/subtask/delete")
    fun deleteSubtask (
        @Header("Authorization") authorization: String,
        @Body request: DeleteSubtask
    )


    @GET("/api/subtask/listSubTask")
    fun getSubtask (
        @Header("Authorization") authorization: String,
        @Body request: TaskIDRequest
    ): Call<SubtaskList>


    @PUT("/api/subtask/completed")
    fun completeSubtask(
        @Header("Authorization") authorization: String,
        @Body request: DeleteSubtask
    ): Call<Task>

    @POST("/api/routine/create")
    fun createRoutine (
        @Header("Authorization") authorization: String,
        @Body request: CreateRoutine
    )


    @PUT("/api/routine/delete")
    fun deleteRoutine (
        @Header("Authorization") authorization: String,
        @Body request: DeleteRoutine
    )


    @PUT("/api/routine/update")
    fun updateRoutine (
        @Header("Authorization") authorization: String,
        @Body request: UpdateRoutine
    )

    @GET("/api/routine/delete")
    fun getRoutineByID (
        @Header("Authorization") authorization: String,
        @Body request: DeleteRoutine
    ):Call<Routine>

    @GET("/api/routine/getlistRoutinebyUserID")
    fun getRoutineByUserID ( @Header("Authorization") authorization: String,):Call<ListRoutine>


    @GET("/api/routine/getlistRoutinebyDate")
    fun getRoutineByDate ( @Header("Authorization") authorization: String,):Call<ListRoutine>

    @PUT("/api/routine/completed")
    fun completedRoutine (
        @Header("Authorization") authorization: String,
        @Body request: CompletedRoutine
    )

    @POST("/api/template/create")
    fun createTemplate (
        @Header("Authorization") authorization: String,
        @Body request: CreateTemplate
    )


    @POST("/api/template/update")
    fun updateTemplate (
        @Header("Authorization") authorization: String,
        @Body request: UpdateTemplate
    )

    @PUT("/api/template/delete")
    fun deleteTemplate (
        @Header("Authorization") authorization: String,
        @Body request: TemplateIDRequest
    )

    @GET("/api/template/getListTemplate")
    fun getListTemplate (
        @Header("Authorization") authorization: String,
    ): Call<TemplateDate>

    @GET("/api/template/getTemplate/{id}")
    fun getTemplate(
        @Header("Authorization") authorization: String,
        @Path("id") TemplateID: String,
    ): Call<TaskByTemplateDate>

    @GET("/api/task/getCountTask")
    fun getCountTask(
        @Header("Authorization") authorization: String,
    ): Call<completedTask>

    companion object {
        fun create(): TaskAPI {
            val taskClient : TaskAPI = Retrofit.Builder()
                .baseUrl("http://192.168.10.217:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskAPI ::class.java)
            return taskClient
        }
    }

}