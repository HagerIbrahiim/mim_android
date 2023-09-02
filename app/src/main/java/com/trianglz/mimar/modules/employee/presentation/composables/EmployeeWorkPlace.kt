package com.trianglz.mimar.modules.employee.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXXSmall
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType

@Composable
fun EmployeeWorkPlace(offeredLocation: () -> OfferedLocationType, showShimmer: () -> Boolean) {

    val showAtBranch = remember(offeredLocation()) {
        offeredLocation() is OfferedLocationType.Branch ||
                offeredLocation() is OfferedLocationType.Both
    }

    val showAtHome = remember(offeredLocation()) {
        offeredLocation() is OfferedLocationType.Home ||
                offeredLocation() is OfferedLocationType.Both
    }

    val showAndSymbol =  remember(offeredLocation()) {
                offeredLocation() is OfferedLocationType.Both
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXSmall)
    ) {
        AnimatedVisibility(visible = showAtBranch, modifier = Modifier.shimmer(showShimmer()),) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.iconSizeXXSmall),
                tint = MaterialTheme.colors.secondary,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_shop_outlined),
                contentDescription = null
            )
        }
        AnimatedVisibility(visible = showAndSymbol, modifier = Modifier.shimmer(showShimmer()),) {
            
            Text(text = stringResource(id = R.string.and_symbol),
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.W400
                ), )

        }
        AnimatedVisibility(visible = showAtHome, modifier = Modifier.shimmer(showShimmer()),) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.iconSizeXXSmall),
                tint = MaterialTheme.colors.secondary,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_outlined),
                contentDescription = null
            )
        }
    }
}