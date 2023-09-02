package com.trianglz.mimar.modules.user_home.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.composables.TitleItem
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.model.TitleItemUIModel
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenSections
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenTitleAndList
import com.trianglz.mimar.modules.branches.presentation.composables.BranchItem
import com.trianglz.mimar.modules.branches.presentation.composables.RequiredLocationBranchItem
import com.trianglz.mimar.modules.user_home.presentation.model.BranchesSectionUIModel

/**
 * Created by hassankhamis on 05,January,2023
 */

@Composable
fun BranchesSectionItem(
    locationEnabled: () -> Boolean,
    onRequestLocationClicked: () -> Unit,
    onChangeLocationClicked: () -> Unit,
    branchSection: () -> BranchesSectionUIModel,
    ) {
    val branchSectionItem = remember {
        branchSection()
    }
    val itemSize = remember {
        if (branchSectionItem.list.size == 1) {
            1f
        } else {
            0.9f
        }
    }

    val content: @Composable () -> Unit = remember {
        {
            if (locationEnabled() ) {
                if (branchSectionItem.list.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsDefault),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideDefault)
                    ) {
                        items(branchSectionItem.list, key = { it.id }) {
                            BranchItem(
                                item = { it },
                                modifier = Modifier.fillParentMaxSize(itemSize)
                            )
                        }
                    }
                } else {
                    RequiredLocationBranchItem({false}, onChangeLocationClicked)
                }
            } else {
                RequiredLocationBranchItem({true}, onRequestLocationClicked)
            }
        }
    }

    val titleItemUIModel = remember {
        TitleItemUIModel(
            title = branchSectionItem.branchSectionType.name,
            titleColor = { MaterialTheme.colors.primary },
            seeMoreColor = { MaterialTheme.colors.secondary },
            showShimmer = branchSectionItem.showShimmer,
            isAllCaps = false,
            hasSeeMore = locationEnabled(),
            modifier = Modifier.padding(top = 4.dp),
            onSeeMoreClicked = branchSectionItem.onSeeMoreClicked
        )
    }
    Column(
        modifier = Modifier
            .padding(bottom = MaterialTheme.dimens.spaceBetweenSections),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenTitleAndList)
    ) {
        TitleItem(
            titleItemUIModel = titleItemUIModel.copy(
                seeMoreTitle = { stringResource(id = R.string.view_all) } ),
        )
        content()
    }
}