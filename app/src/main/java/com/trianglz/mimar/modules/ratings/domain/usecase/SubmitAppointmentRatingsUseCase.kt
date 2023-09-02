package com.trianglz.mimar.modules.ratings.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel
import com.trianglz.mimar.modules.ratings.domain.model.SubmitAppointmentReviewDomainModel
import com.trianglz.mimar.modules.ratings.domain.repository.RatingsRepository
import javax.inject.Inject

class SubmitAppointmentRatingsUseCase @Inject constructor(private val repo: RatingsRepository) :
    BaseUseCase {
    suspend fun execute(ratings: SubmitAppointmentReviewDomainModel){
        repo.submitAppointmentRating(ratings)
    }
}