package com.trianglz.mimar.modules.services.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

@Composable
fun ServicesPaginatedList(
    source: () -> ComposePaginatedListDataSource<ServiceUIModel>,
    isSwipeEnabled: () -> Boolean,
    onRefresh: () -> Unit,

    ) {


    val state = rememberLazyListState()

    LaunchedEffect(key1 = source().loadingState.value,){
        if(source().loadingState.value.isLoading) {
            state.scrollToItem(0)
        }
    }

    ComposePaginatedList(
        swipeEnabled = isSwipeEnabled() ,
        state = state,
        networkErrorModifier = Modifier.verticalScroll(rememberScrollState()),
        onRefresh = onRefresh,
        lazyListModifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.dimens.screenGuideDefault),
        paginatedListSource = source(),
        spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
        listItem = { _, item ->
            ServiceItem { item }

        },
        emptyListPlaceholder = {

            Column(
                modifier = Modifier.fillParentMaxSize()
            ) {
                MimarPlaceholder(
                    modifier = {
                        Modifier
                            .fillParentMaxWidth()
                            .weight(1f)
                    },
                    animationFile =  R.raw.search_placeholder ,
                    titleFirstText = { R.string.oops },
                    titleSecondText = { R.string.no_services_found },
                ) {

                }
                Spacer(
                    modifier = Modifier.height(
                        MaterialTheme.dimens.buttonHeight
                                + MaterialTheme.dimens.screenGuideDefault
                    )
                )
            }


        },
    )
}