package com.trianglz.mimar.modules.verification.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.PeriwinkleCrayola

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: MutableState<String>,
    otpCount: Int = 5,
    onOTPFilled: () -> Unit,
    onOtpTextChange: (String, Boolean) -> Unit
) {

    val imeAction = remember(otpText.value) {
        if(otpText.value.length + 1 == otpCount || otpText.value.length  == otpCount)
            ImeAction.Done
        else
            ImeAction.Next
    }
    BasicTextField(modifier = modifier,
        value = otpText.value,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it, it.length == otpCount)
                if (it.length == otpCount) onOTPFilled.invoke()
            }
        },

        keyboardActions = KeyboardActions(
            onNext = {  },

        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword,
            imeAction = imeAction),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index, text = otpText.value
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int, text: String
) {
    val isFocused = text.length == index
    val color: @Composable () -> Color = remember(isFocused) {
        {
            if (isFocused) {
                PeriwinkleCrayola
            } else {
                MaterialTheme.colors.background
            }
        }
    }
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .requiredSize(54.dp)
            .border(
                1.dp, color = color.invoke(), RoundedCornerShape(10.dp)
            )
            .background(MaterialTheme.colors.background, RoundedCornerShape(10.dp))
            .padding(2.dp)
    ) {
        Text(
            modifier = Modifier.align(Center),
            text = char,
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.primary, fontSize = 18.sp, fontWeight = FontWeight.W600
            ),
            textAlign = TextAlign.Center
        )
        if (char.isNotEmpty()) Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.innerPaddingXSmall)
                .align(BottomCenter)
        )
    }
}
