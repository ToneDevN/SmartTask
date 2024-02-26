package com.example.myfirstapp.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserClass(



    @Expose
@SerializedName("firstname") val firstname: String,
    @Expose
@SerializedName("lastname") val lastname: String,
    @Expose
@SerializedName("email") val email: String,
    @Expose
@SerializedName("password") val password: String,
    @Expose
@SerializedName("ProfileImg") val profile_img: String

) {}

