package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.ifNotNull
import com.trianglz.mimar.common.presentation.ui.theme.xSmall


@Composable
fun CountryCodeIcon(
    countryIcon: Int,
    countryCode: String,
    onPhoneIconClicked: (() -> Unit)?
) {
    Row (Modifier.padding( end = 12.dp )){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.xSmall)
                .ifNotNull(onPhoneIconClicked){
                    Modifier.clickable(onClick = onPhoneIconClicked?:{})
                }
                .fillMaxHeight()
                .padding(start = 18.dp , end = 10.dp )

        ) {

            ImageFromRes(
                imageId = countryIcon,
                 modifier = Modifier.clip(CircleShape).size(20.dp)
            )

            Spacer(modifier = Modifier.padding(start = 12.dp))

            Text(
                text = countryCode,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.padding(start = 6.dp))

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                contentDescription = "",
            )


        }

        Divider(
            color = MaterialTheme.colors.primary.copy(alpha = .3F),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically)
        )

    }

}