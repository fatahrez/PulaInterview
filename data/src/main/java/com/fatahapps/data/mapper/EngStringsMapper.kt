package com.fatahapps.data.mapper

import com.fatahapps.data.local.model.EngStringsLocal
import com.fatahapps.data.remote.dto.survey.EngStringsDTO
import com.fatahapps.domain.entities.survey.EngStringsEntity

fun EngStringsDTO.toLocal(): EngStringsLocal =
    EngStringsLocal(
        qFarmerName,
        qFarmerGender,
        optMale,
        optFemale,
        optOther,
        qSizeOfFarm
    )

fun EngStringsLocal.toDomain(): EngStringsEntity =
    EngStringsEntity(
        qFarmerName,
        qFarmerGender,
        optMale,
        optFemale,
        optOther,
        qSizeOfFarm
    )
