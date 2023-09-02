package com.trianglz.mimar.modules.setup_profile.presentation.composables

import com.trianglz.mimar.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.common.presentation.compose_views.ImageWithTitle
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.filter.presenation.model.SelectionType

@Composable
fun EditProfileBottomSheetContent(
    hasDelete: () -> Boolean,
    onTakePhotoClicked: () -> Unit,
    onUploadClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {

    val bottomPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsXLarge.plus(
                MaterialTheme.dimens.bottomNavigationHeight
            )
        }
    }

    val horizontalArrangement: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium.plus(4.dp)
        }
    }

    val verticalArrangement: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingLarge.plus(10.dp)
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(
                top = MaterialTheme.dimens.spaceBetweenItemsXLarge,
                bottom = bottomPadding(),
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault
            ),
        verticalArrangement = Arrangement.spacedBy(verticalArrangement())
    ) {
        BottomSheetTopSection(
            title = { StringWrapper(R.string.edit_profile_picture) },
            topPadding = 0.dp,
            onBackButtonClicked = onCloseClicked
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(horizontalArrangement())
        ) {
            ImageWithTitle(
                modifier = { Modifier.weight(1f) },
                titleRes = { R.string.take_photo },
                iconRes = { R.drawable.ic_camera_icon },
                onClicked = onTakePhotoClicked
            )

            ImageWithTitle(
                modifier = { Modifier.weight(1f) },
                titleRes = { R.string.choose_photo },
                iconRes = { R.drawable.upload_icon },
                onClicked = onUploadClicked
            )

            if (hasDelete())
                ImageWithTitle(
                    modifier = { Modifier.weight(1f) },
                    titleRes = { R.string.delete },
                    iconRes = { R.drawable.delete_icon },
                    onClicked = onDeleteClicked,
                    iconModifier = Modifier.padding(horizontal = 2.dp)
                )

        }

    }

}