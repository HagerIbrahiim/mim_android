/*
 * *
 *  * Created by Ahmed Awad on 1/10/23, 2:46 PM
 *
 */

/*
 * *
 *  * Created by Ahmed Awad on 1/10/23, 12:47 PM
 *
 */

package com.trianglz.mimar.common.presentation.compose_views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.Shapes

@Composable
fun MimarButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    enabled: Boolean = true,
    isAddAlphaToDisabledButton: Boolean = true,
    disabledColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {

    val disabledAlpha = remember {
        if (isAddAlphaToDisabledButton) 0.5F else 1F
    }
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight)
            .clip(Shapes.medium),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.onPrimary,
            disabledContentColor = MaterialTheme.colors.onPrimary,
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledColor.copy(alpha = disabledAlpha)
        ),
        enabled = enabled
    ) {
        Text(
            text = stringResource(id = text),
            style = textStyle
        )
    }

}