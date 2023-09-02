package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import com.trianglz.mimar.R

@Composable
fun TextFieldLeadingIcon(
    keyboardType: KeyboardType,
    customLeadingIcon: Int?
) {

    val icon = remember {
        when (keyboardType) {
            KeyboardType.Password -> R.drawable.ic_password_icon
            KeyboardType.Email -> R.drawable.ic_messages_icon
            else -> customLeadingIcon
        }
    }


    icon?.let {
        Image(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            modifier = Modifier
        )
    }


}