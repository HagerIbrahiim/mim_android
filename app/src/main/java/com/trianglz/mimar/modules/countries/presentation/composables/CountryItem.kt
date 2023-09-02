package com.trianglz.mimar.modules.countries.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel

@Composable
fun CountryItem(country: () -> CountryUIModel,
                countryItemSelected: (Int) -> Unit) {

    val context = LocalContext.current

    val backgroundColor  : @Composable () -> Color =  remember(country().selected) {
        { if (country().selected) MaterialTheme.colors.background else MaterialTheme.colors.surface }
    }

    val onCountryClick = remember {
        {
            countryItemSelected(country().uniqueId)
        }
    }

    val primaryColor  : @Composable () -> Color =  remember {
        { MaterialTheme.colors.primary }
    }

    val onBackgroundColor : @Composable () -> Color = remember {
        { MaterialTheme.colors.onBackground }
    }

    val textColor  : @Composable () -> Color =  remember(country().selected) {
        if(country().selected)  primaryColor  else onBackgroundColor
    }

    val countryRes = remember(country().shortCode) {
        CountryUIModel.getCountryImageResourceId(context, country().shortCode,)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.xSmall)
            .background(backgroundColor())
            .clickable(enabled = !country().showShimmer, onClick = onCountryClick)
            .padding(all = MaterialTheme.dimens.screenGuideDefault),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageFromRes(
            imageId = countryRes,
            modifier = Modifier.size(20.dp).shimmer(country().showShimmer).clip(CircleShape),

        )

        Spacer(modifier = Modifier.padding(end = MaterialTheme.dimens.spaceBetweenItemsXSmall))

        Text(
            text = country().dialCode.plus(" - ").plus(country().name),
            style = MaterialTheme.typography.body2.copy(color = textColor(),
            fontWeight = FontWeight.W500),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1F)
                .shimmer(country().showShimmer)
        )
    }
}