package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.cart.presentation.model.NoteUIModel


@Composable
fun CartNoteSection(value: NoteUIModel) {
    val note by remember {
        value.note
    }

//    Box(
//        modifier = Modifier
//            .wrapContentSize()
//            .padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
//            .clip(MaterialTheme.shapes.xxSmall)
//
//    ) {
    if (note.isEmpty()) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
        TextStartsWithIcon(
            iconSize = MaterialTheme.dimens.iconSizeXSmall,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
                .clip(MaterialTheme.shapes.xxSmall)
                .clickWithThrottle {
                    value.onClick.invoke(note)
                }
                .padding(
                    horizontal = MaterialTheme.dimens.screenGuideXSmall,
                    vertical = MaterialTheme.dimens.innerPaddingXXSmall
                ),
            drawableRes = R.drawable.ic_note,
            iconTint = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.primary,
            data = stringResource(id = R.string.leave_a_note),
            textStyle = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )
    } else {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)) {
            Text(
                text = stringResource(id = R.string.note),
                modifier = Modifier.padding(vertical = MaterialTheme.dimens.spaceBetweenItemsXLarge),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.W600
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        MaterialTheme.colors.selectedItemBackground.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.xSmall
                    )
                    .clip(MaterialTheme.shapes.xSmall)
                    .clickWithThrottle {
                        value.onClick.invoke(note)
                    }
                    .padding(all = MaterialTheme.dimens.innerPaddingLarge)
            ) {
                Text(
                    text = note,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.primary
                )
            }
        }

    }
//    }


}