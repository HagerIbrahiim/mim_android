/*
 * *
 *  * Created by Ahmed Awad on 1/10/23, 12:39 PM
 *
 */

package com.trianglz.mimar.modules.sign_in.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.RomanSilver

@Composable
fun SignUpWithDividersSection(modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        Divider(
            color = MaterialTheme.colors.background, thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        Text(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            text = stringResource(id = R.string.sign_up_with),
            textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = RomanSilver,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500
            )
        )
    }
}