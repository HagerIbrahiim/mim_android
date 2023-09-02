package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType


@Composable
fun AppointmentPaymentMethod(
    data: () -> PaymentMethodType?,
    isLoading: () -> Boolean,
    showEditBtn: () -> Boolean,
    onEditBtnClicked: () -> Unit,
) {
    val dataSpacing: @Composable () -> Dp = remember {
        {
            if(showEditBtn())
                MaterialTheme.dimens.innerPaddingXXSmall + 2.dp
            else
                MaterialTheme.dimens.innerPaddingLarge
        }
    }

    val paymentIconSize : @Composable () -> Dp = remember(data()?.key) {
        {
            if(data()?.key ==  PaymentMethodType.Online().key)
                MaterialTheme.dimens.iconSizeXXSmall
            else
                MaterialTheme.dimens.iconSizeMedium
        }
    }

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .shadow(
                elevation = 0.5.dp,
                shape = MaterialTheme.shapes.small,
                spotColor = MaterialTheme.colors.primary.copy(alpha = .5F)
            )
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.surface)
            .clickable(enabled = showEditBtn(), onClick = onEditBtnClicked)
            .padding(
                start = MaterialTheme.dimens.innerPaddingLarge, end = dataSpacing(),
                top = dataSpacing(), bottom = dataSpacing()
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextStartsWithIcon(
            iconSize = paymentIconSize(),
            modifier = Modifier
                .shimmer(
                    isLoading(),
                ),
            textStyle = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            ),
            drawableRes = data()?.icon ?: R.drawable.price_icon,
            iconTint = MaterialTheme.colors.secondary,
            data = data()?.displayName?.let { StringWrapper(it).getString(context) }?.capitalize() ?: ""
        )

        Spacer(modifier = Modifier.weight(1F))

        if (showEditBtn()) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.xxSmall)
                    .clickable(onClick = onEditBtnClicked)
                    .padding(MaterialTheme.dimens.innerPaddingXXSmall),
            ) {

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "",
                    modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium)
                )
            }
        }
    }

}
