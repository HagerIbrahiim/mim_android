package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel


@Composable
fun AddAnotherServiceSection(
    canAddAnotherService: State<Boolean>,
    onAddAnotherServiceClicked: () -> Unit,
    onConflictClicked: (ValidationMessageUIModel) -> Unit
) {
    val canAddService by remember {
        canAddAnotherService
    }

    val itemAlpha = remember(canAddService) {
        if (canAddService) 1f else 0.5f
    }

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
            .clip(MaterialTheme.shapes.xxSmall)
            .clickWithThrottle(enabled = canAddService, onClick = onAddAnotherServiceClicked)
            .padding(
                horizontal = MaterialTheme.dimens.screenGuideXSmall,
                vertical = MaterialTheme.dimens.innerPaddingXXSmall
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextStartsWithIcon(
            iconSize = MaterialTheme.dimens.iconSizeXSmall,
            modifier = Modifier,
            drawableRes = R.drawable.ic_add,
            iconTint = MaterialTheme.colors.primary.copy(alpha = itemAlpha),
            textColor = MaterialTheme.colors.primary.copy(alpha = itemAlpha),
            data = stringResource(id = R.string.add_another_service),
            textStyle = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )
        if (canAddService.not()) {
            IconButton(
                onClick = {
                    context.resources.apply {
                        onConflictClicked.invoke(
                            ValidationMessageUIModel(
                                message = getString(R.string.you_can_not_add_another_service),
                                desc = getString(R.string.you_must_select_a_starting_time_for_selected_service_to_be_able_to_schedule_more_services)
                            )

                        )
                    }

                },
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.iconSizeXLarge)
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_alert),
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "",
                    modifier = Modifier.padding(MaterialTheme.dimens.innerPaddingXXXSmall)

                )
            }
        }
    }

}
