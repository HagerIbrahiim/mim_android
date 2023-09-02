package com.trianglz.mimar.modules.ratings.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.ratings.domain.model.RatingDomainModel
import com.trianglz.mimar.modules.ratings.domain.repository.RatingsRepository
import javax.inject.Inject

class SubmitFilterRatingUseCase @Inject constructor(private val repo: RatingsRepository) :
    BaseUseCase {
    fun execute(ratings: List<RatingDomainModel>){
        repo.submitFilterRating(ratings)
    }
}