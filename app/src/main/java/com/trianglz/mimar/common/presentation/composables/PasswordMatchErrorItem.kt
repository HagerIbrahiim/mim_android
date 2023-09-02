package com.trianglz.mimar.common.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.mimar.R

@Composable
fun PasswordMatchErrorItem(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
            contentDescription = null, tint = MaterialTheme.colors.error
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.weight(1F),
            text = stringResource(id = R.string.password_must_match),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.error,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        )
    }
}