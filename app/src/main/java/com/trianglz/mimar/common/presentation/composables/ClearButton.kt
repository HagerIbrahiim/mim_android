package com.trianglz.mimar.common.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.innerPaddingXXLarge
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall


@Composable
fun ClearButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    enabled: Boolean = true,
    isAddAlphaToDisabledButton: Boolean = true,
    disabledColor: Color = MaterialTheme.colors.background,
    backgroundColor: Color = MaterialTheme.colors.background,
    onClick: () -> Unit
) {

    val disabledAlpha = remember {
        if (isAddAlphaToDisabledButton) 0.5F else 1F
    }
    Button(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp)),
        onClick = onClick,
        border = BorderStroke(
            MaterialTheme.dimens.borderMedium,
            MaterialTheme.colors.primary
        ),
        shape = MaterialTheme.shapes.xxSmall,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.surface,
            disabledContentColor = MaterialTheme.colors.surface,
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledColor.copy(alpha = disabledAlpha)
        ),
        enabled = enabled
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = text),
            style = textStyle.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = W400,
                fontSize = 14.sp
            ), maxLines = 1, overflow = TextOverflow.Ellipsis
        )
    }

}


@Preview
@Composable
fun previewTechneButton() {
    ClearButton(text = R.string.change) {}
}
