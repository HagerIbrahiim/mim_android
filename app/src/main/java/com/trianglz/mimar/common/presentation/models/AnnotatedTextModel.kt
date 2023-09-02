package com.trianglz.mimar.common.presentation.models

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import com.trianglz.core.domain.model.StringWrapper
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
data class AnnotatedTextModel(
    val text: StringWrapper,
    val style: @Composable () -> TextStyle = { MaterialTheme.typography.subtitle1 },
    val skipSpace: () -> Boolean = { false },
    val onClick: (() -> Unit)? = null,
)
