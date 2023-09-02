package com.trianglz.mimar.common.presentation.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(20.dp)
)

val Shapes.xxSmall: CornerBasedShape get() =
    RoundedCornerShape(8.dp)

val Shapes.xxxSmall: CornerBasedShape get() =
    RoundedCornerShape(4.dp)

val Shapes.xSmall: CornerBasedShape get() =
    RoundedCornerShape(10.dp)

val Shapes.xLarge: CornerBasedShape get() =
    RoundedCornerShape(30.dp)

val Shapes.topRoundedCornerShapeLarge : CornerBasedShape get()  = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)

val Shapes.topRoundedCornerShapeMedium : CornerBasedShape
    get()  = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)

val Shapes.bottomRoundedCornerShapeMedium : CornerBasedShape
    get()  = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)