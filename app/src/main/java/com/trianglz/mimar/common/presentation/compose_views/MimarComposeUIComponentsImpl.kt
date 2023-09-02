package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.mimar.common.presentation.composables.GradientButton
import com.trianglz.mimar.common.presentation.ui.theme.Sycamore

class MimarComposeUIComponentsImpl : BaseComposeMainUIComponents {

    @Composable
    override fun AppButton(
        text: Int,
        modifier: Modifier,
        textStyle: TextStyle,
        enabled: Boolean,
        isAddAlphaToDisabledButton: Boolean,
        disabledColor: Color,
        backgroundColor: Color,
        onClick: () -> Unit
    ) {

        val gradient =
            horizontalGradient(
                colors = listOf(
                    MaterialTheme.colors.primary,
                    Sycamore,
                    MaterialTheme.colors.primary,
                )
            )
        GradientButton(
            text = stringResource(id = text),
            gradient = gradient,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = enabled,
            isAddAlphaToDisabledButton = isAddAlphaToDisabledButton,
            disabledColor = disabledColor, backgroundColor = backgroundColor, textStyle = textStyle

        ) {
            onClick()
        }

    }

}

