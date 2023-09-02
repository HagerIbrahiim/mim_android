package com.trianglz.mimar.common.presentation.compose_views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextStartsWithIcon(
    @DrawableRes drawableRes: Int,
    data: String,
    iconTint: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colors.onSurface,
    iconSize : Dp = 12.dp,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1.copy(
        color = textColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500
    )

) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(id = drawableRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(iconSize),
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = data,
            textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = textStyle
        )
    }
}

@Composable
fun TextStartsWithIcon(
    @DrawableRes drawableRes: Int,
    data: AnnotatedString,
    iconTint: Color = Color.Unspecified,
    textColor: Color = MaterialTheme.colors.onSurface,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = drawableRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(12.dp),
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = data,
            textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400
            )
        )
    }
}
