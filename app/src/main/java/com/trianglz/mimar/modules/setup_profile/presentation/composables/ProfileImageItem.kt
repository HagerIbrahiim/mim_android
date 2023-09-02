package com.trianglz.mimar.modules.setup_profile.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem

@Composable
fun ProfileImageItem(
    image: Any?,
    imageModifier: Modifier,
    modifier: Modifier = Modifier,
    clickAction: () -> Unit
) {

    val onClick = remember {
        {
            clickAction.invoke()
        }
    }
    Box(modifier = modifier) {

        ImageItem(
            image = image,
            modifier = imageModifier
                .align(Alignment.TopCenter)
               ,
            animation = CircularRevealPlugin(duration = 10),
            placeholder = R.drawable.ic_anyone
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .shadow(8.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.BottomEnd)
                .clickWithThrottle {
                    onClick.invoke()
                }
        ) {

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(4.dp)
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera_icon),
                contentDescription = null
            )
        }
    }
}