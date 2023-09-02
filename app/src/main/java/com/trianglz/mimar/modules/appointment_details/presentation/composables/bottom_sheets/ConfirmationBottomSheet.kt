package com.trianglz.mimar.modules.appointment_details.presentation.composables.bottom_sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXXLarge
import com.trianglz.mimar.modules.appointment_details.presentation.model.ConfirmationDialogType
import com.trianglz.mimar.modules.appointment_details.presentation.model.getMessage

@Composable
fun ConfirmationBottomSheet(
    confirmationDialogType: () -> ConfirmationDialogType,
    okBtnClicked: () -> Unit,
    onCloseBottomSheetClicked: () -> Unit
) {

    val buttonsSpacing: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.buttonHeight + MaterialTheme.dimens.spaceBetweenItemsXXXLarge + MaterialTheme.dimens.screenGuideDefault
        }
    }

    val message : @Composable () -> AnnotatedString = remember(confirmationDialogType()) {
        {
            confirmationDialogType().getMessage()
        }
    }

    BottomSheetRoundedContainerWithButton(
        primaryButtonText = { R.string.yes },
        secondaryButtonText = { R.string.no },
        onPrimaryButtonClicked = {
            onCloseBottomSheetClicked()
            okBtnClicked() },
        onSecondaryButtonClicked = onCloseBottomSheetClicked,

        ) {
        Column {
            BottomSheetTopSection(
                title = { StringWrapper(confirmationDialogType().title) },
                topPadding = MaterialTheme.dimens.screenGuideLarge,
                onBackButtonClicked = onCloseBottomSheetClicked,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXXXLarge))

            Text(
                text = message(),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W500,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            )


            Spacer(modifier = Modifier.height(buttonsSpacing()))
        }



    }
}