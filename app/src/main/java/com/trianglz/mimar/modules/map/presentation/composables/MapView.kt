package com.trianglz.mimar.modules.map.presentation.composables

import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.places.api.Places


@Composable
fun MapViewWithCustomMarker(
    mapViewId: () -> Int,
    mapKey: () -> String,
    location: () -> Location?,
    mapModifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    isMiniMap: () -> Boolean = { false },
    showPinIcon: () -> Boolean = { true },
    pinIconSize: () -> Dp = { 40.dp },
    @DrawableRes currentLocationIcon: () -> Int,
    onSaveCameraPosition: (CameraPosition) -> Unit = {},
    onMoveCamera: () -> Unit = {}

) {
    Box(modifier = containerModifier) {

        MapViewContainer(
            mapViewId,
            mapKey,
            location,
            modifier = mapModifier,
            isMiniMap = isMiniMap,
            onSaveCameraPosition = onSaveCameraPosition,
            onMoveCamera = onMoveCamera
        )

        if(showPinIcon()){
            MapPinOverlay(pinIconSize, currentLocationIcon)
        }

    }
}

@Composable
fun MapPinOverlay(pinIconSize: () -> Dp = { 40.dp }, @DrawableRes currentLocationIcon: () -> Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.size(pinIconSize()),
                imageVector = ImageVector.vectorResource(id = currentLocationIcon()),
                contentDescription = "Pin Image"
            )
        }
        Box(
            Modifier.weight(1f)
        ) {}
    }
}

@Composable
fun MapViewContainer(
    mapViewId: () -> Int,
    mapKey: () -> String,
    location: () -> Location?,
    modifier: Modifier = Modifier,
    isMiniMap: () -> Boolean = { false },
    onSaveCameraPosition: (CameraPosition) -> Unit = {},
    onMoveCamera: () -> Unit = {}

) {
    val mapView = rememberMapViewWithLifecycle(mapViewId, mapKey)

    AndroidView(
        factory = {
            mapView
        },
        modifier = modifier
    ) {

        mapView.getMapAsync { map ->
            map.uiSettings.setAllGesturesEnabled(!isMiniMap())
            location()?.let {
                val position = LatLng(it.latitude, it.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
            }

            map.setOnCameraIdleListener {
                val cameraPosition = map.cameraPosition
                onSaveCameraPosition(cameraPosition)
            }
            map.setOnCameraMoveStartedListener {
                onMoveCamera()
            }
        }

    }
}


/**
 * Remembers a MapView and gives it the lifecycle of the current LifecycleOwner
 */
@Composable
fun rememberMapViewWithLifecycle(
    mapViewId: () -> Int,
    mapKey: () -> String,
): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = mapViewId.invoke()
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapKey,mapView, context)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
private fun rememberMapLifecycleObserver(
    mapKey: () -> String,
    mapView: MapView,
    context: Context
): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Places.initialize(context, mapKey.invoke())
                    mapView.onCreate(Bundle())
                }
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }