package com.trianglz.mimar.modules.branches.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R

@Composable
fun RequiredLocationBranchItem(locationAccessRequired: () -> Boolean, onClick: () -> Unit) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .background(MaterialTheme.colors.onPrimary, shape = MaterialTheme.shapes.large)
            .padding(start = MaterialTheme.dimens.innerPaddingLarge)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NoAvailableBranchesDescription(modifier = Modifier.weight(1f), locationAccessRequired = locationAccessRequired, onClick = onClick)
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_required),
                modifier = Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }

    }
}