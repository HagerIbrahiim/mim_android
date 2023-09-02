package com.trianglz.mimar.modules.notification.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXXSmall
import com.trianglz.mimar.modules.notification.presentation.model.NotificationUIModel

@Composable
fun NotificationItem(notification: () -> NotificationUIModel) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.surface)
            .ifTrue(notification().seen == true) {
                Modifier
                    .background(Color.White)
                    .alpha(0.5F)
            }
            .clickWithThrottle(!notification().showShimmer) {
                notification().onNotificationClicked(notification())
            }.padding(MaterialTheme.dimens.screenGuideDefault)

        ,
    ) {

        Column(modifier = Modifier.weight(1F)
            //.background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = notification().title ?: "",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W600,
                color = MaterialTheme.colors.primary),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .shimmer(notification().showShimmer)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXXXSmall))

            Text(
                text = notification().body ?: "",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W500,
                    color = MaterialTheme.colors.primary),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .shimmer(notification().showShimmer)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXXSmall))

            Text(
                text = notification().formattedDate ?: "",
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.widthIn(min = 40.dp).shimmer(notification().showShimmer)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceBetweenItemsLarge))
        // Hardcoded size as it will not be used anywhere else
        Spacer(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.spaceBetweenItemsXXSmall)
                .size(10.dp)
                .clip(CircleShape)
                .shimmer(notification().showShimmer)
                .ifTrue(notification().seen?.not() == true) {
                    Modifier.background(
                        MaterialTheme.colors.secondaryVariant
                    )
                }

        )


    }
}
