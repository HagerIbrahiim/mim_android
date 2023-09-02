package com.trianglz.mimar.common.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.trianglz.core_compose.presentation.compose_ui.BaseColors
import javax.inject.Inject

val Charcoal = Color(0xFF3F4752)
val AntiqueBrass = Color(0xFFD19985)
val Emerald = Color(0xFF43C564)
val Carnelian = Color(0xFFBA1A1A)
val GhostWhite = Color(0xFFECEEF3)
val Cultured = Color(0xFFF8F9FC)
val RomanSilver = Color(0xFF888D94)
val PeriwinkleCrayola = Color(0xFFBBC7DB)
val DeepPeach = Color(0xFFFFBEA3)
val AthensGray = Color(0xFFE9EBEF)

val ColorAccent = Charcoal
val Sycamore = Color(0xFF888D94)
val Black80 = Color(0xCC000000)
val Black20 = Color(0xCC000000)
val White85 = Color(0xD9FFFFFF)
val White50 = Color(0x80FFFFFF)
val Rating = Color(0xFFFECF6A)

val Iron = Color(0xFFDCDDDE)
val LightSteelBlue = Color(0xFFBBC7DB)
val TropicalViolet = Color(0xFFD3A7DE)
val EtonBlue = Color(0xFF85D1A8)
val Silver = Color(0xFFD9D9D9)



class MimarLightColors @Inject constructor(): BaseColors() {
    override val primary: Color
        get() = Charcoal
    override val secondary: Color
        get() = AntiqueBrass
    override val onPrimary: Color
        get() = GhostWhite
    override val onSecondary: Color
        get() = Color.White
    override val surface: Color
        get() = Color.White
    override val success: Color
        get() = Emerald
    override val error: Color
        get() = Carnelian
    override val onSurface: Color
        get() = Charcoal
    override val background: Color
        get() = Cultured
    override val onBackground: Color
        get() = RomanSilver
    override val onError: Color
        get() = Color.White
    override val primaryVariant: Color
        get() = Charcoal
    override val secondaryVariant: Color
        get() = AntiqueBrass
}

val Colors.searchBackground  @Composable get() = if (isSystemInDarkTheme()) GhostWhite else GhostWhite
val Colors.rippleColor  @Composable get() = if (isSystemInDarkTheme()) GhostWhite else GhostWhite
val Colors.dividerColor  @Composable get() = if (isSystemInDarkTheme()) GhostWhite else GhostWhite
val Colors.ratingColor  @Composable get() = if (isSystemInDarkTheme()) Rating else Rating
val Colors.ratingBackgroundColor  @Composable get() = if (isSystemInDarkTheme()) Silver else Silver
val Colors.addressCheckedItemColor  @Composable get() = if (isSystemInDarkTheme()) PeriwinkleCrayola else PeriwinkleCrayola
val Colors.addressUnCheckedItemColor  @Composable get() = if (isSystemInDarkTheme()) AthensGray else AthensGray

val Colors.selectedItemBackground  @Composable get() = if (isSystemInDarkTheme()) PeriwinkleCrayola else PeriwinkleCrayola
val Colors.settingsLanguageColor  @Composable get() = if (isSystemInDarkTheme()) PeriwinkleCrayola else PeriwinkleCrayola

val Colors.successColor  @Composable get() = if (isSystemInDarkTheme()) Emerald else Emerald
