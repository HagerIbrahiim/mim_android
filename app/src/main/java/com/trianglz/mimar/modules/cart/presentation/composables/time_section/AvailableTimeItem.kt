package com.trianglz.mimar.modules.cart.presentation.composables.time_section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.ui.theme.innerPaddingXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.selectedItemBackground
import com.trianglz.mimar.common.presentation.ui.theme.xxxSmall
import com.trianglz.mimar.modules.time.presentation.model.TimeUIModel


@Composable
fun AvailableTimeItem(item: () -> TimeUIModel) {
    val selectedTime by remember { item().isSelected }

    val textColor: @Composable () -> Color = remember(selectedTime) {
        { if (selectedTime) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground }
    }

    val backgroundColor: @Composable () -> Color = remember(selectedTime) {
        { if (selectedTime) MaterialTheme.colors.selectedItemBackground else MaterialTheme.colors.onPrimary }
    }

    Text(
        text = item().title,
        style = MaterialTheme.typography.body2.copy(
            fontWeight = FontWeight.W400,
            color = textColor()
        ),
        modifier = Modifier
            .clip(MaterialTheme.shapes.xxxSmall)
            .background(backgroundColor())
            .clickable(
                enabled = !item().showShimmer,
                onClick = { item().onClick.invoke(item().uniqueId) })
            .padding(
                vertical = MaterialTheme.dimens.innerPaddingXXSmall,
                horizontal = MaterialTheme.dimens.innerPaddingMedium
            )
            .shimmer(item().showShimmer, shimmerWidth = 0.3f),
    )
}
