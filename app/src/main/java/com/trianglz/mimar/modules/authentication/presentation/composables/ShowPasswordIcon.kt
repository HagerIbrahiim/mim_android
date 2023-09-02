package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle

@Composable
fun ShowPasswordIcon(icon: Int, onPasswordIconClicked: () -> Unit) {

    Box(modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .clickable {
            //indication = null,
            //interactionSource = remember { MutableInteractionSource() },
            onPasswordIconClicked()
        }) {
        Icon(
            ImageVector.vectorResource(id = icon),
            contentDescription = null, modifier = Modifier.align(Alignment.Center)
        )
    }
}

