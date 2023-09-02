package com.trianglz.mimar.modules.branches.data.remote.mapper

import com.trianglz.mimar.modules.branches.data.remote.model.BranchReviewDataModel
import com.trianglz.mimar.modules.branches.domain.model.BranchReviewDomainModel

fun BranchReviewDataModel.toDomain() = BranchReviewDomainModel(
   id = id,
   rating = rating,
   feedback = feedback ,
   name = customerName,
   date = createdDate
)