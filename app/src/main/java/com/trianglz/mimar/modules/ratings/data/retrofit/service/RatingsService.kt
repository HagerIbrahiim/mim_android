package com.trianglz.mimar.modules.ratings.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import retrofit2.http.Body
import retrofit2.http.POST
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel

interface RatingsService {

    @POST(ApiPaths.BRANCH_REVIEWS)
    suspend fun submitBranchReviews(@Body submitAppointmentRequestModel : SubmitAppointmentRequestModel): SuccessMessageResponse

}