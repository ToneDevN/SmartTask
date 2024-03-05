package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Subtask(
    @Expose
    @SerializedName("SubtaskID") val subtaskID: Int,
    @Expose
    @SerializedName("TitleSubTask") val titleSubTask: String,
    @Expose
    @SerializedName("Completed") var completed: Boolean,
    @Expose
    @SerializedName("TaskID") val taskID: Int,
): Parcelable {}

@Parcelize
data class SubtaskList(
    @Expose
    @SerializedName("subTask") val subtaskList: List<Subtask>
): Parcelable {}