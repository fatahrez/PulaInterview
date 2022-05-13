package com.fatahapps.presentation.mapper

import com.fatahapps.domain.entities.survey.EngStringsEntity
import com.fatahapps.presentation.model.survey.EngStrings

fun EngStringsEntity.toPresentation(): EngStrings =
    EngStrings(
        qFarmerName,
        qFarmerGender,
        optMale,
        optFemale,
        optOther,
        qSizeOfFarm
    )