package com.trianglz.mimar.modules.countries.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.core_compose.presentation.pagination.ComposePaginatedList
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.compose_views.SearchTextField
import com.trianglz.mimar.common.presentation.compose_views.SideScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel

@Composable
fun CountriesScreenContent(
    showSearchTextFiled: () -> Boolean,
    showPlaceHolder: () -> Boolean,
    source: () -> ComposePaginatedListDataSource<CountryUIModel>,
    onCountryClick: (Int) -> Unit,
    searchText: () -> MutableState<String?>,
    onBackButtonClicked : () -> Unit) {
    Column(
        modifier = Modifier
            .setStatusBarPadding()
            .calculateBottomPadding()
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {


        SideScreenHeader(
            modifier = { Modifier.padding(all = MaterialTheme.dimens.screenGuideDefault) },
            text = { stringResource(id = R.string.select_country) },
            closeClicked = onBackButtonClicked
        )

        if (showSearchTextFiled()) {

            SearchTextField(
                searchText =  searchText ,
                hintText = { stringResource(id = R.string.search_here) },
            )
        }

        Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.spaceBetweenItemsXSmall * 2))

        Divider(modifier = Modifier
            .background(Iron)
            .height(MaterialTheme.dimens.borderSmall)
        )

        ComposePaginatedList(
            lazyListModifier = Modifier.fillMaxSize(),
            paginatedListSource = source(),
            listItem = { _, item ->
                CountryItem(
                    { item }, onCountryClick
                )

            },
            emptyListPlaceholder = {

                if(showPlaceHolder()){
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                    ) {
                        MimarPlaceholder(
                            modifier = {
                                Modifier
                                    .fillParentMaxWidth()
                                    .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                                    .weight(1f)
                            },
                            animationFile =  R.raw.search_placeholder ,
                            titleFirstText = { R.string.oops },
                            titleSecondText = { R.string.no_results_found },
                            descriptionText = { StringWrapper(R.string.try_another_key) },) {

                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.buttonHeight
                                + MaterialTheme.dimens.screenGuideDefault))
                    }
                }

            },
        )
    }
}