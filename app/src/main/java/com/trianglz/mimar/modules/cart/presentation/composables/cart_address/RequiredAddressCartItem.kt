package com.trianglz.mimar.modules.cart.presentation.composables.cart_address

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.GeneralMultiColorPartiallyClickableText
import com.trianglz.mimar.common.presentation.models.AnnotatedTextModel
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall

@Composable
fun RequiredAddressCartItem(onClick: () -> Unit) {

    val textStyle: @Composable () -> TextStyle = remember {
        {
            MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colors.primary,
            )
        }
    }

    val texts = remember {
            listOf(
//                AnnotatedTextModel(
//                    StringWrapper(R.string.no_address_yet_to_confirm_your_appointment_please),
//                    style = textStyle
//                ),

                AnnotatedTextModel(StringWrapper(R.string.select_an_address), style = {

                    textStyle().copy(
                        color = MaterialTheme.colors.secondary,
                        textDecoration = TextDecoration.Underline
                    )
                }, onClick = onClick),
            )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
//            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .background(MaterialTheme.colors.onPrimary, shape = MaterialTheme.shapes.xxSmall)
            .padding(horizontal = MaterialTheme.dimens.innerPaddingLarge, vertical = MaterialTheme.dimens.innerPaddingSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GeneralMultiColorPartiallyClickableText(
                modifier = Modifier.weight(1f),
                texts = texts,
                maxLines = Int.MAX_VALUE,
                textAlign = TextAlign.Start
            )
//            NoAvailableBranchesDescription(modifier = Modifier.weight(1f), locationAccessRequired = locationAccessRequired, onClick = onClick)
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_address_placeholder),
                modifier = Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }

    }
}