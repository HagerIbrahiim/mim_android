package com.trianglz.mimar.modules.branch_reviews.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.branches.presentation.model.BranchReviewUIModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchReviewsUseCase
import com.trianglz.mimar.common.presentation.mapper.toUI
import com.trianglz.mimar.modules.branches.presentation.mapper.toUI
import javax.inject.Inject

class BranchReviewsSource @Inject constructor(
    private val getBranchReviewsUseCase: GetBranchReviewsUseCase
) : ComposePaginatedListDataSource<BranchReviewUIModel>(
    autoInit = false,
    shimmerList = BranchReviewUIModel.getShimmerList()
) {

    var branchId: Int? = null

    override suspend fun getPage(page: Int, perPage: Int): List<BranchReviewUIModel> {
        return getBranchReviewsUseCase.execute(page,perPage, branchId ?:-1).map { it.toUI() }
    }
}
