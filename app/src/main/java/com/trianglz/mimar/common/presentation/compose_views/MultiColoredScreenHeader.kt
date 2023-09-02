package com.trianglz.mimar.common.presentation.compose_views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultiColoredScreenHeader(
    @StringRes firstText: Int,
    @StringRes secondText: Int,
    onBackClicked: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 6.dp, end = 40.dp)
    ) {
        val onClick = remember {
            {
                onBackClicked()
            }
        }
        BackButtonCompose {
            onClick.invoke()
        }

        MultiStyleText(
            firstText = stringResource(id = firstText),
            firstColor = MaterialTheme.colors.primary,
            secondText = stringResource(id = secondText),
            secondColor = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier.weight(1F),
        )

    }
}
