package com.trianglz.mimar.common.presentation.compose_views.bottom_sheet

import com.trianglz.mimar.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import com.trianglz.mimar.common.presentation.compose_views.ButtonGradientBackground
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.xSmall


@Composable
fun BottomSheetRoundedContainerWithButton(
    containerModifier: @Composable () -> Modifier = { Modifier.padding(bottom = MaterialTheme.dimens.bottomNavigationHeight + MaterialTheme.dimens.screenGuideDefault) },
    buttonsContainerModifier: @Composable () -> Modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
    containerBackgroundColor: @Composable () -> Color = { MaterialTheme.colors.surface },
    primaryButtonText: () -> Int? = { null },
    secondaryButtonText: () -> Int? = { null },
    isPrimaryButtonEnabled: () -> Boolean = { true },
    showPrimaryButton: () -> Boolean = { true },
    disabledBtnColor: @Composable () -> Color = { MaterialTheme.colors.primary },
    backgroundBtnColor: @Composable () -> Color = { MaterialTheme.colors.primary },
    onPrimaryButtonClicked: () -> Unit = {},
    onSecondaryButtonClicked: () -> Unit = {},
    content: @Composable (BoxScope.() -> Unit)
) {

    BottomSheetTopRoundedCorners(containerModifier, containerBackgroundColor) {

        content()

        if (showPrimaryButton()) {


            ButtonGradientBackground(containerModifier = Modifier.align(Alignment.BottomCenter).then(
                buttonsContainerModifier()
            )) {
                Row(
                    modifier = Modifier.fillMaxWidth()

                ) {
                    secondaryButtonText()?.let {
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
                            onClick = onSecondaryButtonClicked
                        ) {
                            secondaryButtonText()?.let {
                                AutoSizedText(
                                    text = stringResource(id = it),
                                    textStyle = MaterialTheme.typography.button.copy(
                                        color = MaterialTheme.colors.primary,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }

                        }

                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsSmall))
                    }

                    BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                        modifier = Modifier.weight(1F),
                        text = primaryButtonText() ?: R.string.ok,
                        enabled = isPrimaryButtonEnabled(),
                        textStyle = MaterialTheme.typography.button,
                        isAddAlphaToDisabledButton = true,
                        disabledColor = disabledBtnColor(),
                        backgroundColor = backgroundBtnColor(),
                    ) {
                        if (isPrimaryButtonEnabled()) onPrimaryButtonClicked()
                    }
                }

            }

        }
    }
}

