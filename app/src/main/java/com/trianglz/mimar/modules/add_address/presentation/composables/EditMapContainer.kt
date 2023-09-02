package com.trianglz.mimar.modules.add_address.presentation.composables

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Sycamore
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.modules.map.presentation.composables.MapViewWithCustomMarker

@Composable
fun EditMapContainer(currentLocation: () -> Location?, editAddressClicked: () -> Unit) {
    val onEditAddressButtonClicked = remember {
        { editAddressClicked() }
    }

    val innerPaddingXSmall = MaterialTheme.dimens.innerPaddingXSmall
    val context = LocalContext.current

    ConstraintLayout(Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault)) {
        val (map, editBtn) = createRefs()

        MapViewWithCustomMarker(
            { R.id.map },
            { context.getString(R.string.google_maps_key) },
            currentLocation,
            mapModifier = Modifier.clip(MaterialTheme.shapes.xxSmall),
            containerModifier = Modifier
                .height(118.dp)
                .constrainAs(map) {
                    top.linkTo(parent.top)
                    linkTo(parent.start, parent.end)
                },
            currentLocationIcon = { R.drawable.current_location},
            isMiniMap = { true },
        )


        Text(
            text = stringResource(id = R.string.edit),
            style = MaterialTheme.typography.button,
            modifier = Modifier
                .shadow(4.dp, MaterialTheme.shapes.xxSmall)
                .clip(MaterialTheme.shapes.xxSmall)
                .background(MaterialTheme.colors.primary)
                .clickable(onClick = onEditAddressButtonClicked)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            Sycamore,
                            MaterialTheme.colors.primary,
                        )
                    )
                )
                .padding(horizontal = 38.dp, vertical = innerPaddingXSmall.minus(2.dp))
                .constrainAs(editBtn) {
                    end.linkTo(map.end, innerPaddingXSmall)
                    bottom.linkTo(map.bottom, innerPaddingXSmall)
                }
        )
    }
}