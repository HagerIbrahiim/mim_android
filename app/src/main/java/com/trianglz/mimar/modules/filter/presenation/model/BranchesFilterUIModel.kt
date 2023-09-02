package com.trianglz.mimar.modules.filter.presenation.model

import android.os.Parcelable
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel
import com.trianglz.mimar.modules.serviced_genders.presenation.model.ServicedGenderUIModel
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class BranchesFilterUIModel(
    val specialties: List<SpecialtiesUIModel>?=null,
    val offeredLocations: List<OfferedLocationsUIModel>?=null,
    val selectedRating: Float?=null,
    val genderList: List<ServicedGenderUIModel>?=null,
    val pickedTime: LocalTime?=null,
    val pickedDate: LocalDate?=null,

    ) : Parcelable