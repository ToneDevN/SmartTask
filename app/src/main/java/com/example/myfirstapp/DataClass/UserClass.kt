package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserClass(
    @Expose
    @SerializedName("Firstname") val firstname: String,
    @Expose
    @SerializedName("Lastname") val lastname: String,
    @Expose
    @SerializedName("Email") val email: String,
    @Expose
    @SerializedName("profileImg") val profile_img: String,
    @Expose
    @SerializedName("token") val token: String,
): Parcelable {}

