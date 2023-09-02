package com.trianglz.mimar.modules.user_home.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.composables.GradientButton
import com.trianglz.mimar.common.presentation.ui.theme.DeepPeach
import com.trianglz.mimar.common.presentation.ui.theme.GhostWhite

@Composable
fun GuestWelcomeComposable(onGetStartedClick: () -> Unit) {
    val gradient =
        horizontalGradient(
            colors = listOf(
                MaterialTheme.colors.secondary,
                DeepPeach
            )
        )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .background(GhostWhite, shape = MaterialTheme.shapes.large)
            .padding(all = MaterialTheme.dimens.innerPaddingLarge)
    ) {
        Column() {
            Text(
                text = stringResource(id = R.string.welcome_to_mimar),
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.welcome_guest_message),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                GradientButton(
                    text = stringResource(id = R.string.get_started),
                    gradient = gradient,
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .wrapContentSize()
                        .wrapContentHeight(),
                ) {
                    onGetStartedClick()
                }
            }
        }

    }
}
