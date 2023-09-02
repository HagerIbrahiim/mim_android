package com.trianglz.mimar.common.presentation.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.trianglz.core.presentation.enums.MessageDialogMode
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.noRippleClickable
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MessageDialog(
    mode: () -> MessageDialogMode,
    text: () -> String,
    animationTime: () -> Int = { 200 },
    dismiss: () -> Unit
) {

    val messageIcon  = remember(mode()) {
        if(mode() is MessageDialogMode.Error)
            R.drawable.ic_error_alert
        else
            R.drawable.success
    }

    AnimatedTransitionDialog(onDismissRequest = { dismiss() }, animationTime = animationTime) {

        Row(
            Modifier
                .clip(MaterialTheme.shapes.xSmall)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(MaterialTheme.dimens.screenGuideMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageFromRes(imageId = messageIcon, modifier = Modifier)

            Spacer(modifier = Modifier.padding(start = MaterialTheme.dimens.innerPaddingSmall * 2))

            Text(
                text = text(),
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AnimatedScaleInTransition(
    animationTime: () -> Int,
    visible: () -> Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible(),
        enter = scaleIn(
            animationSpec = tween(animationTime())
        ),
        exit = scaleOut(
            animationSpec = tween(animationTime())
        ),
        content = content
    )
}


@Composable
fun AnimatedTransitionDialog(
    animationTime: () -> Int,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(animationTime().toLong())
            animateTrigger.value = true
        }
    }

    val onDismiss = remember {
        {
            coroutineScope.launch {
                animateTrigger.value = false
                delay(animationTime().toLong())
                onDismissRequest()
            }
        }
    }



    Popup(
        onDismissRequest = onDismissRequest,
        offset = IntOffset(0, 70),
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable(onClick = { onDismiss() })
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
        ) {
            AnimatedScaleInTransition(animationTime, visible = { animateTrigger.value }) {
                content()
            }
        }
    }
}
