package com.trianglz.mimar.modules.search.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.BackButtonCompose
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.compose_views.SearchTextField
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.searchHeight
import com.trianglz.mimar.modules.branches.presentation.composables.BranchItem
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.search.presentation.model.BaseSearchModel
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.mimar.common.presentation.tabs.compose_views.MimarTabs
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel
import com.trianglz.mimar.modules.services.presentation.composables.ServiceItem
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import  com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
@Composable
fun SearchScreenContent(
    listState: () -> LazyListState,
    tabList: () -> SnapshotStateList<TabItemUIModel>,
    showProvidersTab: () -> Boolean,
    source: () -> ComposePaginatedListDataSource<out BaseSearchModel>,
    searchText: () -> MutableState<String?>,
    onTabChanged: (Int) -> Unit,
    onBackBtnClicked: () -> Unit,
) {

    val spaceBetweenItemsXLargeHalf: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.spaceBetweenItemsXLarge / 2 }
    }

    val topSpacerHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.screenGuideXSmall.plus(2.dp)
        }
    }

    val searchShadowHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.searchHeight - 4.dp
        }
    }

    val searchShadowHorizontalPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall /4
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .statusBarsPadding()
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
    ) {

        //10.dp
        Spacer(modifier = Modifier.height(topSpacerHeight()))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = MaterialTheme.dimens.screenGuideDefault)
        ) {

            BackButtonCompose(Modifier,
                onclick = onBackBtnClicked)

            // Used this custom shadow instead of elevation to enhance shadow look to be like drop shadow
            // instead of shadow covering the whole textField (top,bottom, start, end)
            Box(modifier = Modifier
                .requiredHeight(MaterialTheme.dimens.searchHeight)
                .weight(1F)) {
                Box(
                    modifier = Modifier
                        .requiredHeight(searchShadowHeight())
                        .fillMaxWidth()
                        .padding(horizontal = searchShadowHorizontalPadding())
                        .shadow(
                            MaterialTheme.dimens.cardElevation,
                            shape = MaterialTheme.shapes.small
                        )
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )

                SearchTextField(
                    searchText = searchText,
                    hintText = { stringResource(id = R.string.looking_for_something) },
                    addPadding = { false },
                    background = { MaterialTheme.colors.surface }
                )
            }


        }

        if (searchText().value != null && (searchText().value?.length ?: 0) >= 3) {

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

                MimarTabs(
                    modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
                    list = tabList,
//                    showProviders = { showProvidersTab()  },
                    onTabChanged = onTabChanged,
                )

                Spacer(modifier = Modifier.height(spaceBetweenItemsXLargeHalf()))


            ComposePaginatedList(
                state = listState(),
                disableScrollToFirstItem = true,
                lazyListModifier = Modifier.fillMaxHeight(),
                spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
                paginatedListSource = source(),
                contentPadding = PaddingValues(
                    bottom = MaterialTheme.dimens.screenGuideDefault,
                    start = MaterialTheme.dimens.screenGuideDefault,
                    top =spaceBetweenItemsXLargeHalf(),
                    end = MaterialTheme.dimens.screenGuideDefault
                ),
                listItem = { _, item ->
                    when (item) {
                        is BranchUIModel -> {
                            BranchItem(
                                item = { item },
                            )
                        }

                        is ServiceUIModel -> {
                            ServiceItem { item }
                        }
                    }

                },
                emptyListPlaceholder = {

                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                    ) {
                        MimarPlaceholder(
                            modifier = {
                                Modifier
                                    .fillParentMaxWidth()
                                    .weight(1f)
                            },
                            animationFile =  R.raw.search_placeholder ,
                            titleFirstText = { R.string.oops },
                            titleSecondText = { R.string.no_results_found },
                            descriptionText = { StringWrapper(R.string.try_another_key ) },
                        ) {

                        }
                        Spacer(
                            modifier = Modifier.height(
                                MaterialTheme.dimens.searchHeight
                                        + MaterialTheme.dimens.screenGuideDefault
                            )
                        )
                    }


                },
            )


        } else {

            Column {
                MimarPlaceholder(
                    modifier = {
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                    },
                    animationFile =  R.raw.search_placeholder,
                    titleFirstText = { R.string.search },
                    titleSecondText = { R.string.for__ },
                    customDescription = {
                        MultiStyleText(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            firstText =  stringResource(id = R.string.enter_view_words_search)  ,
                            firstColor = MaterialTheme.colors.primary,
                            secondText = stringResource(id = R.string.mimar) ,
                            secondColor = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            maxLines = Int.MAX_VALUE,
                            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
                            secondTextStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold))
                    },
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.buttonHeight))
            }
           
        }


    }

}