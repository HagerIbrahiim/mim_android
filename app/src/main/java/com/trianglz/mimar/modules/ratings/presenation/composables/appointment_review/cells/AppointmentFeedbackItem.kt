package com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@Composable
fun AppointmentFeedbackItem(state: () -> TextFieldState) {

    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.dimens.spaceBetweenItemsXLarge)
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
    ) {

        TextStartsWithIcon(
            drawableRes = R.drawable.feedback_icon,
            data = stringResource(id = R.string.we_like_hear_from_you),
            iconSize = MaterialTheme.dimens.iconSizeMedium,
            textStyle = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.W600
            )
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))

        BaseTextField(
            imeAction = ImeAction.Default,
            textState = state(),
            validator = TextInputValidator.FreeTextValidator(),
            hint = R.string.share_you_feedback,
            maxLines = Int.MAX_VALUE,
            customHeight = 112.dp,
            backgroundColor = MaterialTheme.colors.background
            )

    }
}