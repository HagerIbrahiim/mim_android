package com.trianglz.mimar.modules.ratings.data.data_source

import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel

interface RatingsRemoteDataSource {
    suspend fun submitAppointmentRating(rating: SubmitAppointmentRequestModel)
}