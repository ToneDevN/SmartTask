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
    @SerializedName("UserID") val userID: Int,
    @Expose
    @SerializedName("FirstName") val firstName: String,
    @Expose
    @SerializedName("LastName") val lastName: String,
    @Expose
    @SerializedName("Email") val email: String,
    @Expose
    @SerializedName("Password") val password: String,
    @Expose
    @SerializedName("ProfileImg") val profileImg: String?, // หรือใช้ประเภทข้อมูลที่ถูกต้องตามต้องการ
    @Expose
    @SerializedName("CreatedAt") val createdAt: String,
    @Expose
    @SerializedName("UpdatedAt") val updatedAt: String,
    @Expose
    @SerializedName("DeletedAt") val deletedAt: String?
): Parcelable {}