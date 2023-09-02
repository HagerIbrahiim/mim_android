package com.trianglz.mimar.modules.appointment_details.presentation.composables.bottom_sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection

@Composable
fun CancellationPolicyBottomSheet(message: () -> String, onBackButtonClicked: () -> Unit) {

    BottomSheetRoundedContainerWithButton(
        showPrimaryButton = { false }
    ) {

        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            BottomSheetTopSection(
                title = { StringWrapper() },
                topPadding = MaterialTheme.dimens.screenGuideLarge,
                onBackButtonClicked = onBackButtonClicked
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

//            Icon(
//                imageVector = ImageVector.vectorResource(id = R.drawable.ic_validation_placeholder),
//                contentDescription = "",
//                tint = Color.Unspecified,
//
//            )
            MimarLottieAnimation(lottieFile = R.raw.error, 100.dp)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

            Text(
                text = message(),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W500,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

        }


    }
}