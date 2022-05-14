package com.fatahapps.presentation.model.survey

data class EngStrings(
    val qFarmerName: String,
    val qFarmerGender: String,
    val optMale: String,
    val optFemale: String,
    val optOther: String,
    val qSizeOfFarm: String
) {
    operator fun iterator(): List<Pair<String, String>> {
        return listOf(
            "qFarmerName" to qFarmerName,
            "qFarmerGender" to qFarmerGender,
            "optMale" to optMale,
            "optFemale" to optFemale,
            "optOther" to optOther,
            "qSizeOfFarm" to qSizeOfFarm
        )
    }
}
