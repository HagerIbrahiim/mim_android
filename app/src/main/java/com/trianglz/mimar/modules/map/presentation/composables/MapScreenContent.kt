package com.trianglz.mimar.modules.map.presentation.composables

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.libraries.maps.model.CameraPosition
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.SearchTextField
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight

@Composable
fun MapScreenContent(
    currentLocation: () -> MutableState<Location?>?,
    searchText: () -> MutableState<String?>,
    fromHome: Boolean,
    saveSelectEnabled: () -> MutableState<Boolean>?,
    onSaveIconClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    currentLocationClicked: () -> Unit,
    backClicked: () -> Unit,
    updateCameraPositionClicked: (CameraPosition) -> Unit,
    moveCamera: () -> Unit,
    ) {

    val statusBarPadding : @Composable () -> Dp = remember {
        {
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding() +
                    MaterialTheme.dimens.screenGuideSmall
        }
    }

    val defaultPadding : @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideDefault }
    }

    val mediumPadding : @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideMedium }
    }
    val largePadding : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.screenGuideLarge
        }
    }
    val context = LocalContext.current

    val bottomPadding : @Composable () -> Dp = remember {
        {
            if (fromHome) MaterialTheme.dimens.bottomNavigationHeight else 0.dp
        }
    }

    Scaffold(
        modifier = Modifier
            .calculateBottomPadding(bottomPadding()),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.White,
                modifier = Modifier.padding(bottom = 70.dp),
                onClick = currentLocationClicked
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_location),
                    "",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        content = { _ ->

            Box(modifier = Modifier.fillMaxSize()) {
                currentLocation()?.value?.let { location ->
                    MapViewWithCustomMarker(
                        { R.id.map },
                        { context.getString(R.string.google_maps_key) },
                        location = { location },
                        containerModifier = Modifier
                            .fillMaxSize(),
                        onSaveCameraPosition = updateCameraPositionClicked,
                        onMoveCamera = moveCamera,
                        currentLocationIcon = { R.drawable.current_location }
                    )
                }

                Row( modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = statusBarPadding(), start = defaultPadding())) {
                    ImageFromRes(
                        imageId = R.drawable.ic_back,
                        tintColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colors.surface)
                            .clickable(onClick = backClicked)
                            .padding(vertical = mediumPadding(), horizontal = largePadding()))

                    SearchTextField(
                        searchText = searchText,
                        enabled = { false },
                        maxLines = { 1 },
                        hintText = { stringResource(id = R.string.search__) },
                        background = { MaterialTheme.colors.surface },
                        onClick = onSearchClicked,
                        addSearchLeadingIcon = { false },
                        modifier = Modifier.weight(1F)
                    )
                }

                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
                        text = R.string.select,
                        enabled = saveSelectEnabled()?.value ?: true ,
                        textStyle = MaterialTheme.typography.button,
                        isAddAlphaToDisabledButton = true,
                        disabledColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = onSaveIconClicked
                    )
                    Spacer(modifier = Modifier.height(defaultPadding()))
                }




            }
        }

    )
}