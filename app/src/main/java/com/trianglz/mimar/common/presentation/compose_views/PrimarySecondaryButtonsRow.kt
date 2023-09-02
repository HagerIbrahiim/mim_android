package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.xSmall

@Composable
fun PrimarySecondaryButtonsRow(
    modifier:  Modifier,
    secondaryText: Int?=null,
    primaryText: Int,
    isPrimaryBtnEnabled: () -> Boolean = { true },
    onSecondaryBtnClicked: () -> Unit,
    onPrimaryBtnClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(
                vertical = MaterialTheme.dimens.screenGuideDefault
            )
            .then(modifier)
    ) {

        secondaryText?.let {
            Button(
                modifier = Modifier
                    .weight(1f)
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
                onClick = onSecondaryBtnClicked
            ) {
                AutoSizedText(
                    text = stringResource(id = secondaryText),
                    textStyle = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsSmall))

        }


        BaseComposeMainUIComponents.LocalMainComponent.AppButton(
            modifier = Modifier.weight(3f),
            text = primaryText,
            enabled = isPrimaryBtnEnabled(),
            textStyle = MaterialTheme.typography.button,
            isAddAlphaToDisabledButton = true,
            disabledColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary,
        ) {
            if(isPrimaryBtnEnabled()) onPrimaryBtnClicked()
        }


    }
}
