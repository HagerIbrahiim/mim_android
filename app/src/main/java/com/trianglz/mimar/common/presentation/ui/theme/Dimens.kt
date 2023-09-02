package com.trianglz.mimar.common.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens
import javax.inject.Inject


@Immutable
@Stable
class MimarDimensImpl @Inject constructor(): BaseDimens {
    // ----------------------------------inner Padding-------------------------------- //

    // 8 , 8, 14, 12, 22, 16, 18, 10
    override val innerPaddingDefault: Dp
        get() = innerPaddingXSmall
    override val innerPaddingLarge: Dp
        get() = 16.dp
    override val innerPaddingMedium: Dp
        get() = 12.dp
    override val innerPaddingSmall: Dp
        get() = 10.dp
    override val innerPaddingXLarge: Dp
        get() = 18.dp
    override val innerPaddingXSmall: Dp
        get() = 8.dp

    // ----------------------------------space Between Items-------------------------------- //
    // 8, 24, 10, 6, 26
    override val spaceBetweenItemsDefault: Dp
        get() = super.spaceBetweenItemsDefault
    override val spaceBetweenItemsLarge: Dp
        get() = 16.dp
    override val spaceBetweenItemsMedium: Dp
        get() = 10.dp
    override val spaceBetweenItemsSmall: Dp
        get() = 8.dp
    override val spaceBetweenItemsXLarge: Dp
        get() = 20.dp
    override val spaceBetweenItemsXSmall: Dp
        get() = 6.dp
    override val cardElevation: Dp
        get() = 4.dp
}
val spaceBetweenItemsXXLarge: Dp
    get() = 24.dp

val BaseDimens.innerPaddingXXLarge get() = 20.dp
val BaseDimens.innerPaddingXXXLarge get() = 24.dp
val BaseDimens.innerPaddingXXSmall get() = 6.dp
val BaseDimens.innerPaddingXXXSmall get() = 4.dp
val BaseDimens.spaceBetweenSections get() = 40.dp
val BaseDimens.spaceBetweenTitleAndList get() = 16.dp
val BaseDimens.categoryImageWidth get() = 84.dp
val BaseDimens.categoryImageHeight get() = 74.dp
val BaseDimens.spaceBetweenItemsXXLarge get() = 26.dp
val BaseDimens.spaceBetweenItemsXXXLarge get() = 30.dp
val BaseDimens.logoImageSize get() = 120.dp

val BaseDimens.searchHeight get() = 50.dp
val BaseDimens.personImageSize: Dp
    get() = 44.dp

val BaseDimens.personImageLarge: Dp
    get() = 86.dp

val BaseDimens.personImageSizeSmall: Dp
    get() = 22.dp

val BaseDimens.dividerThickness: Dp
    get() = 0.5.dp
val BaseDimens.iconSizeLarge: Dp
    get() = 30.dp
val BaseDimens.iconSizeXLarge: Dp
    get() = 40.dp
val BaseDimens.iconSizeSmall: Dp
    get() = 18.dp
val BaseDimens.iconSizeXSmall: Dp
    get() = 16.dp
val BaseDimens.iconSizeXXSmall: Dp
    get() = 14.dp
val BaseDimens.iconSizeXXXSmall: Dp
    get() = 12.dp
val BaseDimens.iconSizeMedium: Dp
    get() = 20.dp
val BaseDimens.bottomNavigationHeight: Dp
    get() = 50.dp
val BaseDimens.ratingIconSize: Dp
    get() = 16.dp
val BaseDimens.tabRowHeight get() = 42.dp

val BaseDimens.headerElevation: Dp
    get() = 1.dp
val BaseDimens.verticalDateItemMinWidth get() = 60.dp
val BaseDimens.appointmentBranchItemHeight get() = 148.dp
val BaseDimens.cardElevationMedium: Dp get() = 1.dp
val BaseDimens.dotSizeSmall: Dp get() = 4.dp
val BaseDimens.spaceBetweenItemsXXSmall get() = 4.dp
val BaseDimens.spaceBetweenItemsXXXSmall get() = 2.dp

val BaseDimens.lottieHeight: Dp
    get() = 280.dp
val errorLottieHeight : Dp get() = 100.dp
