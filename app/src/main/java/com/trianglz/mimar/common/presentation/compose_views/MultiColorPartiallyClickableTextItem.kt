package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import timber.log.Timber

@Composable
fun MultiColorPartiallyClickableText(
    firstText: String,
    firstColor: Color,
    secondText: String,
    secondColor: Color,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    secondTextStyle: TextStyle = style,
    onClick: () -> Unit
) {

    val onSecondTextClicked = remember {
        {
            onClick.invoke()
        }
    }
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = firstColor,
                fontWeight = style.fontWeight,
                fontSize = style.fontSize,
                textDecoration = style.textDecoration

            )
        ) {
            append(firstText)
        }

        append(" ")

        pushStringAnnotation(
            tag = "ClickableText",
            annotation = "clickable_text"
        )
        //add text with your different color/style
        withStyle(
            style = SpanStyle(
                color = secondColor,
                fontWeight = secondTextStyle.fontWeight,
                fontSize = secondTextStyle.fontSize,
                textDecoration = secondTextStyle.textDecoration
            )
        ) {
            append(secondText)
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText
                .getStringAnnotations("ClickableText", offset, offset)
                .firstOrNull()?.let { stringAnnotation ->
                    Timber.tag("Clicked").d(stringAnnotation.item)
                    onSecondTextClicked.invoke()
                }


        },
        overflow = overflow, maxLines = maxLines, modifier = modifier,
    )
}
