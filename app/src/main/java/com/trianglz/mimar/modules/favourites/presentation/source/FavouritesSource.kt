package com.trianglz.mimar.modules.favourites.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.branches.domain.usecase.GetFavoriteBranchesUseCase
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import javax.inject.Inject

class FavouritesSource @Inject constructor(
    private val getFavoriteBranchesUseCase: GetFavoriteBranchesUseCase,

) : ComposePaginatedListDataSource<BranchUIModel>(
    autoInit = false,
    shimmerList = BranchUIModel.getShimmerList()
) {

    var onFavoriteClick: ((item: BranchUIModel) -> Unit) = {  }
    var onClick: ((id: Int) -> Unit) = {  }
    override suspend fun getPage(page: Int, perPage: Int): List<BranchUIModel> {
        return getFavoriteBranchesUseCase.execute(page,perPage).map {
            it.toUIModel(onFavoriteClick = onFavoriteClick, onClick = onClick)
        }
    }
}
