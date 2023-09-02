/*
 * *
 *  * Created by Ahmed Awad on 1/10/23, 3:51 PM
 *
 */

package com.trianglz.mimar.modules.verification.presentation.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.trianglz.core.presentation.helper.MultipleEventsCutter
import com.trianglz.core.presentation.helper.get
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.extensions.noRippleClickable
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.extensions.ifTrue

@Composable
fun TimerItem(
    enabled: () -> State<Boolean>,
    onClick: () -> Unit,
    timeInMinutes: () -> MutableState<Long>,
    timeInSeconds: () -> MutableState<Int>,
) {

    val alpha = remember(enabled().value, enabled()) {
        if (enabled().value) {
            1F
        } else {
            0.6F
        }
    }

    val ctx = LocalContext.current

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val timerText = remember(timeInMinutes().value, timeInSeconds().value) {
        if(timeInMinutes().value == 0L && timeInSeconds().value == 0)
            "" else
        "${timeInMinutes().value} : ${timeInSeconds().value} ${ctx.getString(R.string.mins)}"
    }

    val modifier: @Composable () -> Modifier = remember {
        {
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimens.innerPaddingMedium,
                    vertical = MaterialTheme.dimens.innerPaddingXSmall
                )
                .ifTrue(enabled().value) {
                    Modifier.noRippleClickable {
                        multipleEventsCutter.clickWithThrottle {
                            onClick.invoke()
                        }
                    }
                }
        }
    }

    MultiStyleText(
        firstText = stringResource(id = R.string.resend_code),
        firstColor = MaterialTheme.colors.secondary.copy(alpha),
        secondText = timerText,
        secondColor = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center,
        modifier = modifier.invoke(),
        overflow = TextOverflow.Ellipsis, maxLines = 1,
        style = MaterialTheme.typography.subtitle1.copy(
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400
        )
    )

}