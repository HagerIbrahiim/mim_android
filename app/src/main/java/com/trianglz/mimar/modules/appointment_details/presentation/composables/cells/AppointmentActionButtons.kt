package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.xSmall


@Composable
fun AppointmentActionButtons(
    primaryBtnText: ()-> Int,
    isPrimaryBtnEnabled: () -> Boolean,
    isSecondaryBtnEnabled: () -> Boolean,
    secondaryBtnText: ()-> Int,
    showSecondaryBtn: () -> Boolean,
    secondaryBtnClicked: () -> Unit,
    primaryBtnClicked: () -> Unit
) {

    val buttonsSpacing: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium + MaterialTheme.dimens.spaceBetweenItemsXSmall
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
    ) {

        BaseComposeMainUIComponents.LocalMainComponent.AppButton(
            modifier = Modifier,
            text = primaryBtnText(),
            textStyle = MaterialTheme.typography.button,
            isAddAlphaToDisabledButton = true,
            disabledColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary,
            onClick = primaryBtnClicked,
            enabled = isPrimaryBtnEnabled()
        )

        if(showSecondaryBtn()) {
            Spacer(modifier = Modifier.height(buttonsSpacing()))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
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
                onClick = secondaryBtnClicked,
                enabled = isSecondaryBtnEnabled()
            ) {
                AutoSizedText(
                    text = stringResource(id = secondaryBtnText()),
                    textStyle = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

    }
}