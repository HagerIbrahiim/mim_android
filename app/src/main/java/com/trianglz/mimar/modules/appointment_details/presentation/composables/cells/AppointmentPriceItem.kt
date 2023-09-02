package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R

@Composable
fun AppointmentPriceItem(title: ()->StringWrapper, value: () -> Double?, currency: String?) {

    Row(modifier = Modifier.fillMaxSize()) {
        Text(
            text = title().getString(LocalContext.current),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colors.primary
            )
        )

        Spacer(modifier = Modifier.weight(1F))
        value()?.let {
            Text(
                text = stringResource(
                    id = R.string.price_with_currency,
                    it.toString(),
                    currency?: R.string.saudi_riyal
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.W400,
                    color = MaterialTheme.colors.primary
                )
            )
        }

    }

}