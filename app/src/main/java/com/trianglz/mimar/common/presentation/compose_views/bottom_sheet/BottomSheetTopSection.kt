package com.trianglz.mimar.common.presentation.compose_views.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.SideScreenHeader

@Composable
fun BottomSheetTopSection(
    title: () -> StringWrapper,
    selectAll: () -> Boolean? = { false },
    modifier: () -> Modifier = { Modifier },
    addHorizontalSpacing: Boolean = true,
    topPadding: Dp = MaterialTheme.dimens.screenGuideXLarge ,
    showSelectAllSwitch: () -> Boolean = {false},
    onBackButtonClicked: ()-> Unit,
    onSelectAllSwitchStatusChanged: (isChecked: Boolean) -> Unit ={},
) {
    val horizontalPadding : @Composable () -> Dp = remember {
        { if (addHorizontalSpacing) MaterialTheme.dimens.screenGuideDefault else 0.dp }
    }

    val onSelectAllSwitchChanged: (Boolean) -> Unit = remember {
        {
            onSelectAllSwitchStatusChanged(it)
        }
    }
    Column(
        modifier = modifier()
            .background(MaterialTheme.colors.surface)
            .padding(top = topPadding)
    ) {

            SideScreenHeader(
                modifier = {
                    Modifier.padding(
                        horizontal = horizontalPadding()
                    )
                },
                text = { title().getString(LocalContext.current) },
                closeClicked = onBackButtonClicked
            )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

        if(showSelectAllSwitch()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.screenGuideDefault,
                        end = MaterialTheme.dimens.screenGuideXSmall
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.select_all),
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.W600),
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = MaterialTheme.dimens.innerPaddingXSmall)
                )
                Switch(
                    checked = selectAll() ?: false,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        checkedTrackColor = MaterialTheme.colors.primary,
                    ),
                    onCheckedChange = onSelectAllSwitchChanged)
            }
        }

    }
}
