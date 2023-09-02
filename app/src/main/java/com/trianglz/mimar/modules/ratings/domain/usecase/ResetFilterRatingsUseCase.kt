package com.trianglz.mimar.modules.ratings.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.ratings.domain.repository.RatingsRepository
import javax.inject.Inject

class ResetFilterRatingsUseCase @Inject constructor(private val repo: RatingsRepository) :
    BaseUseCase {
    fun execute(){
        repo.resetFilterRatingData()
    }
}