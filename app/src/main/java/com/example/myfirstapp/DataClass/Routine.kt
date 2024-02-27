package com.example.myfirstapp.DataClass

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListRoutine(
    val routines: List<Routine>
): Parcelable {}

@Parcelize
data class Routine(
    @Expose
    @SerializedName("TaskID") val taskID: Int,
    @Expose
    @SerializedName("Title") val title: String,
    @Expose
    @SerializedName("Description") val description: String,
    @Expose
    @SerializedName("URL") val url: String,
    @Expose
    @SerializedName("Location") val location: String,
    @Expose
    @SerializedName("Priority") val priority: Int,
    @Expose
    @SerializedName("Date") val date: String?,
    @Expose
    @SerializedName("Time") val time: String,
    @Expose
    @SerializedName("CategoryID") val categoryID: Int?,
    @Expose
    @SerializedName("RoutineID") val routineID: Int,
    @Expose
    @SerializedName("Routine") val routineData: RoutineData,
    @Expose
    @SerializedName("Subtask") val subtasks: List<Subtask>
): Parcelable {}

@Parcelize
data class RoutineData(
    @Expose
    @SerializedName("RoutineID") val routineID: Int,
    @Expose
    @SerializedName("DateForm") val dateForm: String,
    @Expose
    @SerializedName("DateTo") val dateTo: String,
): Parcelable {}