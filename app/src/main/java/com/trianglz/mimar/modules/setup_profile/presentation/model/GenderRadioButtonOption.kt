package com.trianglz.mimar.modules.setup_profile.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trianglz.mimar.R


sealed class GenderRadioButtonOption(@StringRes val name: Int, @DrawableRes val icon: Int, val serverName: String){
    object Male : GenderRadioButtonOption(R.string.female, R.drawable.ic_female_icon, "female")
    object Female: GenderRadioButtonOption(R.string.male, R.drawable.ic_male_icon,"male")
}
fun String.getGenderRadioButtonOption() : GenderRadioButtonOption{
    return when(this){
        GenderRadioButtonOption.Male.serverName -> GenderRadioButtonOption.Male
        else ->  GenderRadioButtonOption.Female
    }
}

