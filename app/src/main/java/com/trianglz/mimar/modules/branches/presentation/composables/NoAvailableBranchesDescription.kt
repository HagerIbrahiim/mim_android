package com.trianglz.mimar.modules.branches.presentation.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.GeneralMultiColorPartiallyClickableText
import com.trianglz.mimar.common.presentation.models.AnnotatedTextModel

@Composable
fun NoAvailableBranchesDescription(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    locationAccessRequired: () -> Boolean,
    locationGrantedDescription: () -> Int = { R.string.no_available_providers_nearby },
    locationRequiredDescription : () -> Int = {R.string.to_view_nearby_providers },
    actionText: () -> Int = { R.string.change_your_location },
    onClick: () -> Unit
) {
    val textStyle: @Composable () -> TextStyle = remember {
        {
            MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.W400,
                lineHeight = 160.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
    val texts = remember {
        if (locationAccessRequired()) {
            listOf(
                AnnotatedTextModel(StringWrapper(R.string.you_need_to_allow), style = textStyle),
                AnnotatedTextModel(StringWrapper("\""), style = textStyle),
                AnnotatedTextModel(StringWrapper(R.string.mimar), style = {
                    textStyle().copy(
                        fontWeight = FontWeight.Bold
                    )
                }, skipSpace = { true }),
                AnnotatedTextModel(StringWrapper("\""), skipSpace = { true }, style = textStyle),
                AnnotatedTextModel(StringWrapper(R.string.to), style = textStyle),
                AnnotatedTextModel(StringWrapper(R.string.access_your_location), style = {

                    textStyle().copy(
                        color = MaterialTheme.colors.secondary,
                        textDecoration = TextDecoration.Underline
                    )
                }, onClick = onClick),
                AnnotatedTextModel(
                    StringWrapper(locationRequiredDescription()),
                    style = textStyle
                ),
            )
        } else {
            listOf(
                AnnotatedTextModel(
                    StringWrapper(locationGrantedDescription()),
                    style = textStyle
                ),

                AnnotatedTextModel(StringWrapper(actionText()), style = {

                    textStyle().copy(
                        color = MaterialTheme.colors.secondary,
                        textDecoration = TextDecoration.Underline
                    )
                }, onClick = onClick),
            )
        }
    }

    GeneralMultiColorPartiallyClickableText(
        modifier = modifier,
        texts = texts,
        maxLines = Int.MAX_VALUE,
        textAlign = textAlign
    )
}