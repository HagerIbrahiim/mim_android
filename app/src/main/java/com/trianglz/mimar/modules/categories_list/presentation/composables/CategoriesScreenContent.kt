package com.trianglz.mimar.modules.categories_list.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.ComposeGridPaginatedList
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.modules.categories.presentation.composables.CategoryItem
import com.trianglz.mimar.modules.categories_list.presentation.source.CategoriesSource
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun CategoriesScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    paginatedSource: () -> CategoriesSource?,
    onCategoryItemClicked: (Int) -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .calculateBottomPadding(50.dp)


    ) {
        HeaderShadow(dividerColor = Iron) { _ ->
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.categories)  },
                isAddBackButton = { true },
                isAddPadding = { false },
                modifier = Modifier.padding(end = 0.dp),
                showCartIcon = { true },
                showNotificationIcon = { true },
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }
        paginatedSource.invoke()?.let {
            ComposeGridPaginatedList(
                paginatedListSource = it,
                gridCells = GridCells.Fixed(3),
                horizontalSpacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsXLarge,
                verticaLSpacingBetweenItems = 20.dp,
                contentPadding = PaddingValues(MaterialTheme.dimens.screenGuideMedium),
                listItem = { _, item ->
                    CategoryItem(imageModifier = Modifier
                        .aspectRatio(11f / 10f)
                        .fillMaxWidth(),
                        categoryUIModel = { item },
                        onCategoryItemClicked = onCategoryItemClicked
                    )
                },
                emptyListPlaceholder = {}
            )
        }
    }
}
