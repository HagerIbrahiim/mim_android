package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R

@Composable
fun SkipButtonWithLanguageBtn(
    currentLocale: @Composable() () -> String,
    onSkipClicked: () -> Unit,
    modifier: Modifier = Modifier,
    skipColor: @Composable () -> Color = { MaterialTheme.colors.surface },
    imageTintColor: @Composable () -> Color = { MaterialTheme.colors.surface },
    showSkipBtn: () -> Boolean = { true },
    changeLanguageClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {

        LanguageButtonWithArrow(
            currentLocale = currentLocale,
            imageTintColor,
            onLangButtonClicked = changeLanguageClicked
        )

        Spacer(modifier = Modifier.weight(1F))

        if (showSkipBtn()) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickWithThrottle {
                        onSkipClicked()
                    }
                    .padding(
                        vertical = 2.dp,
                        horizontal = MaterialTheme.dimens.innerPaddingXSmall
                    ),
                text = stringResource(id = R.string.skip),
                textAlign = TextAlign.End, overflow = TextOverflow.Ellipsis, maxLines = 1,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = skipColor(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                ))
        }

    }

}