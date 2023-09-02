package com.trianglz.mimar.modules.addresses.ui.model

import android.os.Parcelable
import com.google.android.libraries.maps.model.LatLng
import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.countries.presentation.mapper.toUI
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressInfoUIModel(
    val country: String?,
    val city: String?,
    val region: String?
) : Parcelable


