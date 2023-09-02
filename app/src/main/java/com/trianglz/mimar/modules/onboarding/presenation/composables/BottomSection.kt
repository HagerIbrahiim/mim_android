package com.trianglz.mimar.modules.onboarding.presenation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.DeepPeach

@Composable
fun BottomSection(
    size: () -> Int,
    index: () -> Int,
    nextButtonHeight: () -> Dp,
    modifier: () -> Modifier,
    onNextClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all=  MaterialTheme.dimens.screenGuideDefault )
            .then(modifier())
    ) {

        //indicators
        IndicatorsList(size = size, index = index)


        Button(
            onClick = onNextClicked,
            modifier = Modifier
                .shadow(5.dp, CircleShape, spotColor = MaterialTheme.colors.secondary)
                .size(nextButtonHeight())
                .clip(CircleShape)
                .align(CenterEnd),
            contentPadding = PaddingValues(),
        ) {
            Box(
                modifier = Modifier
                    .size(nextButtonHeight())
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondary,
                                DeepPeach
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.next_icon),
                    contentDescription = null
                )
            }

        }
    }
}

