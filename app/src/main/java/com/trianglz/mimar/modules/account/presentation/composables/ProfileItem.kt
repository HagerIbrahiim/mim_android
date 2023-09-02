package com.trianglz.mimar.modules.account.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.bottomRoundedCornerShapeMedium
import com.trianglz.mimar.common.presentation.ui.theme.settingsLanguageColor
import com.trianglz.mimar.common.presentation.ui.theme.successColor
import com.trianglz.mimar.common.presentation.ui.theme.topRoundedCornerShapeMedium
import com.trianglz.mimar.modules.account.presentation.model.ProfileItemPosition.*
import com.trianglz.mimar.modules.account.presentation.model.ProfileMainItemModel


@Composable
fun ProfileItem(
    data: ProfileMainItemModel
) {

    val checkedState = remember { mutableStateOf(data.isSwitchChecked) }

    val shape: @Composable () -> Shape = remember(data.itemPosition) {
        {
            when (data.itemPosition) {
                Top -> {
                    MaterialTheme.shapes.topRoundedCornerShapeMedium
                }

                Bottom -> {
                    MaterialTheme.shapes.bottomRoundedCornerShapeMedium
                }

                Middle -> {
                    RectangleShape
                }

            }
        }
    }

    val onClick = remember {
        {
            if (data.hasSwitch)
                checkedState.value = checkedState.value?.not()
            data.onClick.invoke(checkedState.value)
        }
    }

    val itemVerticalSpacing : @Composable () -> Dp = remember {
        { if (!data.hasSwitch) MaterialTheme.dimens.spaceBetweenItemsLarge
        else 0.dp }
    }

    val containerBackground : @Composable () -> Color = remember(data.isSubSetting) {
        {
            if(data.isSubSetting)
                MaterialTheme.colors.background
            else
                MaterialTheme.colors.surface
        }
    }

    val iconTint : @Composable () -> Color = remember(data.isSubSetting) {
        {
            if(data.isSubSetting)
                MaterialTheme.colors.primary
            else
                MaterialTheme.colors.secondary
        }
    }

    val itemEndSpacing: @Composable () -> Dp = remember(data.isSubSetting) {
        {
            if (data.hasSwitch) MaterialTheme.dimens.spaceBetweenItemsMedium
            else if (!data.isSubSetting) MaterialTheme.dimens.spaceBetweenItemsLarge else 0.dp
        }
    }

    val itemStartSpacing: @Composable () -> Dp = remember(data.isSubSetting) {
        {
            if (!data.isSubSetting) MaterialTheme.dimens.spaceBetweenItemsLarge else 0.dp
        }
    }
    val lineHorizontalPadding : @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideDefault * 2 }
    }


    Column {
        Row(
            modifier = data.modifier
                .padding( start = itemStartSpacing(), end = itemEndSpacing() )
                .clip(shape())
                .fillMaxWidth()
                .background(containerBackground())
                .ifTrue(data.isSubSetting) {
                    Modifier.padding(vertical = MaterialTheme.dimens.spaceBetweenItemsLarge)
                }
                .clickWithThrottle(enabled = !data.hasSwitch, onClick = onClick)
                .padding(
                    horizontal = MaterialTheme.dimens.spaceBetweenItemsLarge,
                    vertical = itemVerticalSpacing()
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            data.icon?.let {
                Image(
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = "",
                    modifier = Modifier,
                    contentScale = ContentScale.FillWidth,
                    colorFilter =  ColorFilter.tint(color = iconTint()),
                )
            }

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsMedium))

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = data.title),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.W500
                ),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 1
            )

            data.subTitle?.let {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colors.settingsLanguageColor,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsLarge))
            }

            if (data.hasSwitch) {
                Switch(
                    checked = checkedState.value ?: false,
                    onCheckedChange = {
                        checkedState.value = it
                        data.onClick.invoke(checkedState.value)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.successColor,
                        checkedTrackColor = MaterialTheme.colors.successColor,
                    ),
                )
            } else {
                if(!data.isSubSetting) {
                    ImageFromRes(
                        imageId = R.drawable.next_icon,
                        modifier = Modifier,
                        tintColor = MaterialTheme.colors.primary
                    )
                }
            }
        }


        if (data.itemPosition != Bottom) {
            HorizontalDivider(
                padding = PaddingValues(horizontal = lineHorizontalPadding()),
            )
        }
    }
}
