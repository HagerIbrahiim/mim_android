package com.trianglz.mimar.modules.offered_location.domain.model

import android.os.Parcelable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OfferedLocationType(val key: String, val name: StringWrapper, @DrawableRes val icon: Int):
    Parcelable {
    object Home: OfferedLocationType("home", StringWrapper(R.string.at_home), R.drawable.ic_home_location)
    data class Branch(val address: StringWrapper): OfferedLocationType("in_place", address, R.drawable.ic_branch)
    data class Both(val address: StringWrapper): OfferedLocationType("both", address, R.drawable.ic_branch)

}

fun String?.toOfferedLocationType(address: String? = null): OfferedLocationType {
    val addressWrapper = if (address == null) StringWrapper(R.string.at_branch) else StringWrapper(address)
    return when (this) {
        OfferedLocationType.Home.key -> OfferedLocationType.Home
        OfferedLocationType.Both(addressWrapper).key -> OfferedLocationType.Both(addressWrapper)
        else -> OfferedLocationType.Branch(addressWrapper)
    }
}

fun OfferedLocationType.toTabUIModel(isSelected: MutableState<Boolean>): TabItemUIModel =
    TabItemUIModel(
        title = this.name, image = this.icon, isSelected = isSelected

    )


fun List<OfferedLocationsDomainModel>.getOfferedLocationFilterKey()= when(this.size){
    2 -> OfferedLocationType.Both(StringWrapper("")).key
    1 -> this[0].value
    else -> null
}

fun OfferedLocationType.toList(): List<StringWrapper> {
    return when (this) {
        is OfferedLocationType.Both -> {
            listOf(OfferedLocationType.Home.name, OfferedLocationType.Branch(StringWrapper(R.string.at_branch)).name)
        }
        is OfferedLocationType.Branch ->{
            listOf( OfferedLocationType.Branch(StringWrapper(R.string.at_branch)).name)
        }
        else -> {
            listOf(this.name)
        }
    }
}

