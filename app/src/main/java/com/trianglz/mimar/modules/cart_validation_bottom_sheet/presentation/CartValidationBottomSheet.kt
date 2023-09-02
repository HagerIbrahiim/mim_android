package com.trianglz.mimar.modules.cart_validation_bottom_sheet.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel

@Composable
fun CartValidationBottomSheet(
    validationMessage: () -> ValidationMessageUIModel,
    onClose: () -> Unit
) {

    val context = LocalContext.current

    val listBottomPadding: @Composable () -> Dp = remember() {
        {
            MaterialTheme.dimens.bottomNavigationHeight + MaterialTheme.dimens.screenGuideDefault
        }
    }

    BottomSheetRoundedContainerWithButton(
        containerModifier = {
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        },
        showPrimaryButton = { false },
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                .padding(bottom = listBottomPadding(), top = MaterialTheme.dimens.screenGuideDefault),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(modifier = Modifier.fillMaxWidth()){
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd).size(MaterialTheme.dimens.iconSizeMedium), onClick = onClose
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

            MimarLottieAnimation(lottieFile = R.raw.error, 100.dp)


            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

            validationMessage().message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp,
                    ),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

            validationMessage().desc?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary,
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }

}

