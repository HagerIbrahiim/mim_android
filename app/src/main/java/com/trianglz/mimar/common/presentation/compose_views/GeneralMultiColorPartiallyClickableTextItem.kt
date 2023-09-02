package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.trianglz.mimar.common.presentation.models.AnnotatedTextModel
import timber.log.Timber

@Composable
fun GeneralMultiColorPartiallyClickableText(
    texts: List<AnnotatedTextModel>,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        texts.forEachIndexed { index, annotatedTextModel ->
            //append your initial text
            if (index != 0 && annotatedTextModel.skipSpace().not()) append(" ")
            if (annotatedTextModel.onClick != null) {
                pushStringAnnotation(
                    tag = "ClickableText",
                    annotation = annotatedTextModel.text.getString(context)
                )
            }
            withStyle(
                style = SpanStyle(
                    color = annotatedTextModel.style().color,
                    fontWeight = annotatedTextModel.style().fontWeight,
                    fontSize = annotatedTextModel.style().fontSize,
                    textDecoration = annotatedTextModel.style().textDecoration

                )
            ) {
                append(annotatedTextModel.text.getString(context))
            }
            if (annotatedTextModel.onClick != null) {
                pop()
            }
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText
                .getStringAnnotations("ClickableText", offset, offset).forEachIndexed { index, stringAnnotation ->
                    Timber.tag("Clicked").d(stringAnnotation.item)
                    texts.firstOrNull { it.text.getString(context) == stringAnnotation.item }?.onClick?.invoke()
                }
        },
        style = TextStyle.Default.copy(textAlign = textAlign),
        overflow = overflow, maxLines = maxLines, modifier = modifier,
    )
}
