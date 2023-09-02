package com.trianglz.mimar.modules.onboarding.presenation.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.trianglz.mimar.R


sealed class OnBoardingData(
    @StringRes val titleFirstWord: Int,
    @StringRes val titleSecondWord: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    @RawRes val lottieFile: Int,
) {

    object First : OnBoardingData(
        R.string.on_boarding_one_title_first_word,
        R.string.on_boarding_one_title_second_word,
        R.string.on_boarding_one_description,
        R.drawable.on_bording_one,
        R.raw.onboarding_1
    )

    object Second : OnBoardingData(
        R.string.on_boarding_second_title_first_word,
        R.string.on_boarding_second_title_second_word,
        R.string.on_boarding_second_description,
        R.drawable.on_bording_two,
        R.raw.onboarding_2
    )

    object Third : OnBoardingData(
        R.string.on_boarding_third_title_first_word,
        R.string.on_boarding_third_title_second_word,
        R.string.on_boarding_third_description,
        R.drawable.on_bording_three,
        R.raw.onboarding_3
    )
}
