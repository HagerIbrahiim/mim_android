package com.trianglz.mimar.modules.branches.domain.model

data class BranchReviewDomainModel(
    val id: Int,
    val rating: Int,
    val feedback: String? = null,
    val date: String? = null,
    val name: String? = null,
)
