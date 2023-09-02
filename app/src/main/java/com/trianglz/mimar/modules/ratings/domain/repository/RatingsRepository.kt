package com.trianglz.mimar.modules.ratings.domain.repository

import com.trianglz.mimar.modules.ratings.domain.model.RatingDomainModel
import com.trianglz.mimar.modules.ratings.domain.model.SubmitAppointmentReviewDomainModel

interface RatingsRepository {
    fun getFilterRatingsList() : List<RatingDomainModel>
    fun submitFilterRating(rating: List<RatingDomainModel>)
    fun resetFilterRatingData()
    suspend fun submitAppointmentRating(rating: SubmitAppointmentReviewDomainModel)


}