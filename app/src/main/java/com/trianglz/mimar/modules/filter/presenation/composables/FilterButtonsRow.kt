package com.trianglz.mimar.modules.filter.presenation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.xSmall

@Composable
fun BoxScope.FilterButtonsRow(
    modifier: @Composable () -> Modifier,
    onResetBtnClicked: () -> Unit,
    onApplyBtnClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colors.surface)
            .padding(
                vertical = MaterialTheme.dimens.screenGuideDefault
            ).then(modifier())
    ) {

        Button(
            modifier = Modifier
                .weight(1f)
                .shadow(elevation = 2.dp, shape = MaterialTheme.shapes.xSmall)
                .height(MaterialTheme.dimens.buttonHeight)
                .clip(MaterialTheme.shapes.xSmall)
                .border(
                    MaterialTheme.dimens.borderMedium,
                    MaterialTheme.colors.primary,
                    MaterialTheme.shapes.xSmall
                ),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.onPrimary,
                disabledContentColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.surface,
            ),
            onClick = onResetBtnClicked
        ) {
            AutoSizedText(
                text = stringResource(id = R.string.reset),
                textStyle = MaterialTheme.typography.button.copy(
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsSmall))

        BaseComposeMainUIComponents.LocalMainComponent.AppButton(
            modifier = Modifier.weight(3f),
            text = R.string.apply,
            enabled = true,
            textStyle = MaterialTheme.typography.button,
            isAddAlphaToDisabledButton = true,
            disabledColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary,
        ) {
            onApplyBtnClicked()
        }


    }
}
