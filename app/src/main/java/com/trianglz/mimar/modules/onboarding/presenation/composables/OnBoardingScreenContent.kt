package com.trianglz.mimar.modules.onboarding.presenation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.modules.authentication.presentation.composables.SkipButtonWithLanguageBtn
import com.trianglz.mimar.modules.onboarding.presenation.model.OnBoardingData

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreenContent(
    showSkipBtn: ()-> Boolean,
    items: () -> List<OnBoardingData>,
    state: () -> PagerState,
    currentLocale : @Composable  () -> String,
    changeLanguageClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,

    ) {

    val nextButtonHeight = remember {
        56.dp
    }

    val topSectionHeight = remember {
        40.dp
    }

    Box(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .calculateBottomPadding()

    ) {

        HorizontalPager(
            count = 3,
            state = state(),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)

        ) { page ->
            OnBoardingItem(
                topSectionHeight = { topSectionHeight},
                nextButtonHeight = { nextButtonHeight },
                item = { items()[page] })


        }

        SkipButtonWithLanguageBtn(currentLocale,
            onSkipClicked,
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.screenGuideDefault,
                top = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault - 10.dp)
                .requiredHeight(topSectionHeight),
            { MaterialTheme.colors.primary },
            { MaterialTheme.colors.primary },
            showSkipBtn ,changeLanguageClicked)


        BottomSection(
            modifier = { Modifier.align(Alignment.BottomCenter) },
            size = { items().size },
            index = { state().currentPage },
            nextButtonHeight = { nextButtonHeight },
            onNextClicked = onNextClicked
        )
    }
}