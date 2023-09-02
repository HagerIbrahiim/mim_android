package com.trianglz.mimar.modules.onboarding.presenation.composables


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.composables.CustomLottieAnimation
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.modules.onboarding.presenation.model.OnBoardingData

@Composable
fun OnBoardingItem(
    topSectionHeight: () -> Dp,
    nextButtonHeight: () -> Dp,
    item: () -> OnBoardingData
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = nextButtonHeight()
                        + MaterialTheme.dimens.screenGuideDefault
                        + MaterialTheme.dimens.screenGuideXSmall,
                //top = topSectionHeight()
            )
    ) {

//        Image(
//            imageVector = ImageVector.vectorResource(item().image),
//            contentScale = ContentScale.Crop,
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//        )

        // Height same as onboarding image heigh
        // Fixed height as it will not be used anywhere else
        CustomLottieAnimation(
            lottieFile = item().lottieFile,
            speedValue = 2F,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(400.dp)
        )

       // Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium * 2))

        Text(
            text = stringResource(id = item().titleFirstWord),
            style = Typography.h5,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
            color = MaterialTheme.colors.primary,
        )
        AutoSizedText(
            text = stringResource(id = item().titleSecondWord),
            textStyle = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.secondary,
            ),
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium * 2))

        Text(
            text = stringResource(item().description),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
            style = Typography.body2.copy(
                fontWeight = FontWeight.W400
            ),

            )
    }
}