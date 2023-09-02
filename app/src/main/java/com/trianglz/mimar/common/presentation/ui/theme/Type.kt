package com.trianglz.mimar.common.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.di.qualifiers.LightMode
import com.trianglz.core_compose.presentation.compose_ui.BaseColors
import com.trianglz.mimar.R
import javax.inject.Inject

val MimarFont = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
)
val lightBaseColors: BaseColors = MimarLightColors()

val Typography = Typography(
    defaultFontFamily = MimarFont,
    h1 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    h2 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    h3 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    h4 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    h5 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    h6 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    subtitle1 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    subtitle2 = TextStyle(
        color = lightBaseColors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    body1 = TextStyle(
        color = lightBaseColors.onSecondary,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    overline = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
    button = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)

    ),
)