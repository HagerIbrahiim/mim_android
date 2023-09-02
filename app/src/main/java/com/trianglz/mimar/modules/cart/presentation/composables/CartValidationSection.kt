package com.trianglz.mimar.modules.cart.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.cart.presentation.model.CartValidationSectionUIModel

@Composable
fun CartValidationSection(validationSectionModel: () -> CartValidationSectionUIModel) {

    val validations by remember {
        validationSectionModel().validationMessageUIModel
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.secondary.copy(alpha = .2F))
            .padding(MaterialTheme.dimens.screenGuideMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageFromRes(imageId = R.drawable.ic_error, modifier = Modifier, tintColor = MaterialTheme.colors.secondary)

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsSmall))

        Text(
            text = validations?.message ?: "",
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.secondary,

                )
        )
    }
}