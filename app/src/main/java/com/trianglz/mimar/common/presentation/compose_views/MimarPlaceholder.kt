package com.trianglz.mimar.common.presentation.compose_views

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.common.presentation.ui.theme.lottieHeight


@Composable
fun MimarPlaceholder(
    modifier: @Composable ()-> Modifier,
    @RawRes animationFile:  Int?=null,
    titleFirstText: ()-> Int,
    titleSecondText: ()-> Int,
    descriptionText: ()-> StringWrapper? =  { null },
    customDescription: @Composable (ColumnScope.() -> Unit)? = null,
    buttonText: ()-> Int? = { null },
    centerContent: () -> Boolean = { true},
    placeholderImage: Int? = null,
    imageModifier:  Modifier = Modifier,
    animModifier:  Modifier = Modifier,
    imageContentSpacerHeight : Dp = 44.dp,
    titleDescSpacer: Dp = MaterialTheme.dimens.spaceBetweenItemsXLarge,
    animHeight : Dp? = MaterialTheme.dimens.lottieHeight,
    onButtonClicked : () -> Unit = {},

    ) {

    val arrangement = remember {
        if(centerContent()) Arrangement.Center else Arrangement.Top
    }
    val onPlaceholderButtonClicked = remember {
        { onButtonClicked() }
    }
    val context = LocalContext.current

    Column(modifier = modifier(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement) {

        animationFile?.let {
            MimarLottieAnimation(
                lottieFile = animationFile,
                modifier = Modifier.then(animModifier),
                height = animHeight,
            )
        }?: let {
            placeholderImage?.let { img ->
                ImageFromRes(
                    imageId = img,
                    modifier = Modifier
                        .wrapContentWidth()
                        .then(imageModifier))
            }
        }


        Spacer(modifier = Modifier.height(imageContentSpacerHeight))

        MultiStyleText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            firstText = stringResource(id = titleFirstText()),
            firstColor = MaterialTheme.colors.primary,
            secondText = stringResource(id = titleSecondText()),
            secondColor = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(titleDescSpacer))

        descriptionText()?.let {
            AutoSizedText(
                text = it.getString(context),
                textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        customDescription?.invoke(this)

        buttonText()?.let {

            BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                text = it, enabled = true,
                modifier = Modifier,
                textStyle = MaterialTheme.typography.button,
                isAddAlphaToDisabledButton = true,
                disabledColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.primary,
                onClick = onPlaceholderButtonClicked
            )
        }


    }
}