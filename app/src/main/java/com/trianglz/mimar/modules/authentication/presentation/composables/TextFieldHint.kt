package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.trianglz.mimar.R

@Composable
fun TextFieldHint(hint: Int?) {

    Text(
        text = stringResource(id = hint ?: R.string.empty),
        style = MaterialTheme.typography.body2.copy( fontWeight = FontWeight.W400),
        color = MaterialTheme.colors.onBackground
    )
}
