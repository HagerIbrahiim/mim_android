package com.trianglz.mimar.modules.employee_details.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens

@Composable
fun OfferedLocationItem(name: StringWrapper) {

    val context = LocalContext.current

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.innerPaddingLarge),
        text = name.getString(context),
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.W500
        ),
        maxLines = 1,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis
    )
}