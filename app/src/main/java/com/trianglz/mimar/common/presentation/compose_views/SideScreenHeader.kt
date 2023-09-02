package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium

@Composable
fun SideScreenHeader(
    text: @Composable () -> String,
    modifier: @Composable () -> Modifier = { Modifier },
    closeClicked: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier()),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Spacer(modifier = Modifier.padding(end = MaterialTheme.dimens.innerPaddingXSmall))

        Text(
            modifier = Modifier.weight(1F),
            text = text(),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.subtitle1.copy(
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        )
        
        Spacer(modifier = Modifier.padding(end = MaterialTheme.dimens.innerPaddingXSmall))

        IconButton(
            modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium), onClick = closeClicked
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }


    }

}