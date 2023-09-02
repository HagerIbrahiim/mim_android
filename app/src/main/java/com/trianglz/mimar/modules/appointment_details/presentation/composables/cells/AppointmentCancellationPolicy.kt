package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MultiColorPartiallyClickableText

@Composable
fun AppointmentCancellationPolicy(cancellationPolicyClicked: () -> Unit) {

    val textStyle : @Composable () -> TextStyle = remember {
        {
            MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
            )
        }
    }
    MultiColorPartiallyClickableText(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .padding(horizontal = MaterialTheme.dimens.innerPaddingLarge),
        firstText = stringResource(id = R.string.check_our),
        firstColor = MaterialTheme.colors.primary,
        secondText = "“${stringResource(R.string.cancellation_policy)}”",
        secondColor = MaterialTheme.colors.secondary,
        maxLines = 1,
        style = textStyle(),
        secondTextStyle = textStyle().copy( textDecoration = TextDecoration.Underline),
        onClick = cancellationPolicyClicked
    )
}