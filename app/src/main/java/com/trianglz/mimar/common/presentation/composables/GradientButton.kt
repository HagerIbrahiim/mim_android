package com.trianglz.mimar.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.trianglz.core.presentation.helper.MultipleEventsCutter
import com.trianglz.core.presentation.helper.get
import com.trianglz.mimar.common.presentation.helper.NoRippleInteractionSource
import com.trianglz.mimar.common.presentation.ui.theme.Sycamore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    enabled: Boolean = true,
    isAddAlphaToDisabledButton: Boolean = true,
    disabledColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = Color.Transparent,
    shape: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit,
) {

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val disabledAlpha = remember {
        if (isAddAlphaToDisabledButton) 0.5F else 1F
    }


    val buttonBackground: @Composable Modifier.() -> Modifier = remember(enabled) {
        {
            if (enabled) {
                background(gradient)
            } else {
                background(disabledColor.copy(disabledAlpha))
            }
        }
    }
    Button(
        modifier = modifier.clip(shape),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = disabledColor,
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledColor.copy(alpha = disabledAlpha)
        ),
        contentPadding = PaddingValues(),
        enabled = enabled,
        interactionSource = NoRippleInteractionSource(),
        onClick = {  },
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .buttonBackground()
                .clickable(enabled = enabled) {
                    multipleEventsCutter.clickWithThrottle {
                        onClick()
                    }}
                .then(modifier)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, style = textStyle)
        }
    }
}