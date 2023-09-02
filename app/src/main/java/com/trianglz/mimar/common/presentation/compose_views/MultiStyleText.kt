package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun MultiStyleText(
    firstText: String,
    firstColor: Color,
    secondText: String,
    secondColor: Color,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    secondTextStyle: TextStyle = style,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = firstColor,
                    fontWeight = style.fontWeight,
                    fontSize = style.fontSize
                )
            ) {
                append(firstText)
            }
            withStyle(
                style = SpanStyle(
                    color = secondColor,
                    fontWeight = secondTextStyle.fontWeight,
                    fontSize = secondTextStyle.fontSize
                )
            ) {
                append(" $secondText")
            }
        },
        textAlign = textAlign, overflow = overflow, maxLines = maxLines, modifier = modifier
    )
}

