package com.trianglz.mimar.modules.filter.presenation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.xSmall


@Composable
fun ClickableDropDownField(
    label: () -> Int,
    unSelectedText: () -> Int,
    selectedText:  @Composable () -> String,
    modifier: @Composable () -> Modifier = { Modifier },
    itemsCount: @Composable () -> Int? = { 0 },
    userChangedText: () -> Boolean = {false},
    endIcon: () -> Int? = { R.drawable.ic_arrow_down },
    startIcon: () -> Int? = { null },
    onItemClick: () -> Unit,
    onTrailingIconClicked: () -> Unit = {
        onItemClick()
    },
) {

    val textColor : @Composable () -> Color = remember(itemsCount(), selectedText(), userChangedText()) {
        { if ((itemsCount() ?: 0) > 0 || userChangedText()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground }
    }

    val text : @Composable () -> String = remember(itemsCount(),selectedText(), userChangedText()) {
        {
            if ((itemsCount()
                    ?: 0) > 0 || userChangedText()
            ) selectedText() else stringResource(id = unSelectedText())
        }
    }

    val labelBottomPadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium.plus(4.dp)
        }
    }

    Column {

        Text(
            text = stringResource(id = label()),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W600),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(bottom = labelBottomPadding()))
    }
    Row(
        modifier = modifier()
            .fillMaxWidth()
            .requiredHeight(MaterialTheme.dimens.buttonHeight)
            .clip(MaterialTheme.shapes.xSmall)
            .clickable(onClick = onItemClick)
            .background(MaterialTheme.colors.onPrimary)
            .padding(
                start = MaterialTheme.dimens.screenGuideDefault,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        startIcon()?.let {
            Icon(
                imageVector = ImageVector.vectorResource(id = it),
                contentDescription = null,
                modifier = Modifier,
                tint = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsMedium))
        }

        Text(
            text = text(),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W400,color = textColor()),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1F)
                .padding(end = MaterialTheme.dimens.screenGuideDefault)
        )

        endIcon()?.let {
            IconButton(
                onClick = onTrailingIconClicked,
                modifier = Modifier.padding(vertical = MaterialTheme.dimens.innerPaddingXSmall)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = "icon",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                )
            }
        }

    }
    //  }

}
