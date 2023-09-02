package com.trianglz.mimar.modules.ratings.data.data_source

import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel
import com.trianglz.mimar.modules.ratings.data.retrofit.service.RatingsService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RatingsRemoteDataSourceImpl @Inject constructor(
    private val ratingsService: RatingsService
) : RatingsRemoteDataSource {
    override suspend fun submitAppointmentRating(rating: SubmitAppointmentRequestModel) {
        ratingsService.submitBranchReviews(rating)
    }

}