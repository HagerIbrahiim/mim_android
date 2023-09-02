package com.trianglz.mimar.modules.branches.presentation.mapper

import com.trianglz.mimar.modules.branches.presentation.model.BranchReviewUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchReviewDomainModel

fun BranchReviewDomainModel.toUI() = BranchReviewUIModel(id, rating, feedback, date, name)