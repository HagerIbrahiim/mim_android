package com.trianglz.mimar.modules.branches.presentation.model

import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel

data class BranchReviewUIModel(
    val id: Int,
    val rating: Int = 0,
    val feedback: String? = null,
    val date: String ? = null,
    val name: String? = null,
    override val showShimmer: Boolean = false,
) : PaginatedModel {
    override val uniqueId: Int
        get() = id

    companion object {
        fun getShimmerList(): List<BranchReviewUIModel> {
            val list: ArrayList<BranchReviewUIModel> = ArrayList()
            repeat(10) {
                list.add(
                    BranchReviewUIModel(
                        id = it,
                        rating = 0,
                        showShimmer = true
                    )
                )
            }
            return list
        }
    }
}
