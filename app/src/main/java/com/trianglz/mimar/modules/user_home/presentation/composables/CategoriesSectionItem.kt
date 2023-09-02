package com.trianglz.mimar.modules.user_home.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.TitleItem
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.model.TitleItemUIModel
import com.trianglz.mimar.common.presentation.ui.theme.*
import com.trianglz.mimar.modules.categories.presentation.composables.CategoryItem
import com.trianglz.mimar.modules.user_home.presentation.model.CategoriesSectionUIModel
import com.trianglz.mimar.R

/**
 * Created by hassankhamis on 04,January,2023
 */

@Composable
fun CategoriesSectionItem(
    categoriesSectionUIModel: () -> CategoriesSectionUIModel,
    onCategoryItemClicked: (Int) -> Unit,

    ) {
    val currentList = remember {
        categoriesSectionUIModel().list
    }

    val paddingBottom: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.spaceBetweenSections - 14.dp }
    }

    val titleItemUIModel = remember {
        TitleItemUIModel(
            title = StringWrapper(com.trianglz.mimar.R.string.reach_a_provider_by_category) ,
            titleColor = { MaterialTheme.colors.primary },
            seeMoreColor = { MaterialTheme.colors.secondary },
            showShimmer = categoriesSectionUIModel().showShimmer,
            isAllCaps = false,
            hasSeeMore = true,
            modifier = Modifier.padding(top = 4.dp),
            onSeeMoreClicked = categoriesSectionUIModel().onSeeMoreClicked
        )
    }
    Column(
        modifier = Modifier
            .padding(bottom = paddingBottom()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenTitleAndList)
    ) {
        TitleItem(
            titleItemUIModel = titleItemUIModel.copy(
                seeMoreTitle = { stringResource(id = R.string.view_all) } ),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXXLarge),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideDefault)
        ) {
            items(currentList, key = { it.id }) {
                CategoryItem(parentModifier = Modifier
                    .width(MaterialTheme.dimens.categoryImageWidth)
                    .wrapContentHeight(), imageModifier = Modifier.size(width = MaterialTheme.dimens.categoryImageWidth, height = MaterialTheme.dimens.categoryImageHeight), categoryUIModel = { it },
                onCategoryItemClicked = onCategoryItemClicked)
            }
        }
    }
}