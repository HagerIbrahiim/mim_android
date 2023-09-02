package com.trianglz.mimar.modules.branch_view_all.presentation.composables

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.map.presentation.composables.MapViewWithCustomMarker

@Composable
fun BranchMapItem(
    locationText: () -> StringWrapper,
    lat: () -> Double,
    lng: () -> Double,
    showShimmer: () -> Boolean,
    modifier: Modifier = Modifier, onItemClicked: () -> Unit
) {


    val context = LocalContext.current

    val branchLocation = remember(lat(), lng()) {
        val location = Location("")
        location.latitude = lat()
        location.longitude = lng()
        location
    }

    Column(
        modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.surface)
            .clickable(enabled = !showShimmer(), onClick = onItemClicked)
    ) {

            Box(modifier = Modifier) {
                MapViewWithCustomMarker(
                    { R.id.map },
                    { context.getString(R.string.google_maps_key) },
                    { branchLocation },
                    mapModifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmer(showShimmer()),
                    containerModifier = Modifier.height(130.dp),
                    currentLocationIcon = { R.drawable.current_location },
                    isMiniMap = { true },
                    showPinIcon = { !showShimmer() }
                )

                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(enabled = !showShimmer() , onClick = onItemClicked)
                )

        }

        TitleWithIcons(
            title = locationText,
            showShimmer = showShimmer,
            startIcon = ImageVector.vectorResource(id = R.drawable.ic_location)
        )
    }
}