package com.trianglz.mimar.modules.account.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.modules.account.presentation.model.ProfileTitleModel

@Composable
fun ProfileTitle(profileTitleModel: ProfileTitleModel) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                 MaterialTheme.dimens.innerPaddingXLarge,
            ),
        text = stringResource(id = profileTitleModel.title),
        style = MaterialTheme.typography.subtitle2.copy(
            fontWeight = FontWeight.W600
        ),
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        maxLines = 1
    )
}