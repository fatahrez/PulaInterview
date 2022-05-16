package com.fatahapps.data.local.model.answer

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AnswerLocal(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val bitmap: String?,
    val answerList: List<String>
)
