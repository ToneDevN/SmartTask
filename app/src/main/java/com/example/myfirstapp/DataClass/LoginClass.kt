package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class LoginClass(
    @Expose
    @SerializedName("user") val user: User,
    @Expose
    @SerializedName("token") val token: String
): Parcelable {}
@Parcelize
data class User(
    @Expose
    @SerializedName("FirstName") val firstName: String,
    @Expose
    @SerializedName("LastName") val lastName: String,
    @Expose
    @SerializedName("ProfileImg") val profileImg: String?, // หรือใช้ประเภทข้อมูลที่ถูกต้องตามต้องการ

): Parcelable {}