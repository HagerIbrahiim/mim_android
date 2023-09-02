package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.plugins.ImagePlugin
import com.trianglz.mimar.R

@Composable
fun ImageItem(
    image: Any?,
    animation: ImagePlugin.PainterPlugin = CircularRevealPlugin(duration = 800),
    modifier: Modifier, applyShimmer: Boolean = true, placeholder: Int = R.drawable.placeholder
) {

    val contentScale = remember {
        mutableStateOf(ContentScale.Crop)
    }

    Image(
        painter = rememberAsyncImagePainter(
            model = image,
            error = rememberAsyncImagePainter(placeholder),
            onError = {
                contentScale.value = ContentScale.Inside
            },
            onSuccess = {
                contentScale.value = ContentScale.Crop
            },
        ),
        modifier = modifier,

        alignment = Alignment.Center,
        contentScale = contentScale.value,
                contentDescription = null,
    )

}