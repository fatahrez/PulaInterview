package com.fatahapps.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EngStringsLocal(
    @PrimaryKey val qFarmerName: String,
    val qFarmerGender: String,
    val optMale: String,
    val optFemale: String,
    val optOther: String,
    val qSizeOfFarm: String
)
