package com.trianglz.mimar.modules.branches.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.branches.data.remote.retrofit.response.BranchesListResponse
import com.trianglz.mimar.modules.branches.data.remote.retrofit.response.ToggleFavoriteBranchResponse
import com.trianglz.mimar.modules.branches.data.remote.retrofit.response.BranchDetailsResponse
import com.trianglz.mimar.modules.time.data.remote.response.TimeSlotsListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.trianglz.mimar.modules.branches.data.remote.retrofit.response.BranchReviewsListResponse

interface BranchesService {
    @GET(ApiPaths.BRANCHES)
    suspend fun getBranchesListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.LAT) lat: Double? = null,
        @Query(ApiQueries.LNG) lng: Double? = null,
        @Query(ApiQueries.CATEGORY_ID) categoryId: Int? = null,
        @Query(ApiQueries.NAME) name: String? = null,
        @Query(ApiQueries.LIST) list: String? = null,
        @Query(ApiQueries.SERVICED_GENDER) servicedGender: String? = null,
        @Query(ApiQueries.OFFERED_LOCATION) offeredLocation: String? = null,
        @Query(ApiQueries.RATING) rating: Float? = null,
        @Query(ApiQueries.SPECIALTY_IDS) specialitiesIds: String? = null,
        @Query(ApiQueries.AVAILABLE_DATE) availableDate: String?= null,
        @Query(ApiQueries.AVAILABLE_TIME) availableTime: String?= null,
        ): BranchesListResponse

    @GET(ApiPaths.FAVORITE_BRANCHES)
    suspend fun getFavoriteBranchesListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.CATEGORY_ID) categoryId: Int? = null,
    ): BranchesListResponse


    @POST(ApiPaths.TOGGLE_FAVORITE)
    suspend fun toggleBranchFavoriteAsync(
        @Path(ApiPaths.ID) id: Int,
    ): ToggleFavoriteBranchResponse

    @GET(ApiPaths.GET_BRANCH_DETAILS)
    suspend fun getBranchDetails(
        @Path(ApiPaths.ID) id: Int,
    ): BranchDetailsResponse

    @GET(ApiPaths.GET_OTHER_BRANCHES)
    suspend fun getOtherBranchServiceProviderBranches(
        @Path(ApiPaths.ID) id: Int,
        ): BranchesListResponse


    @GET(ApiPaths.BRANCH_REVIEWS)
    suspend fun getBranchesReviews(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.BRANCH_ID) branchId: Int? = null,
        ): BranchReviewsListResponse

    @GET(ApiPaths.FREE_SLOTS)
    suspend fun getBranchAvailableSlotsAsync(
        @Path(ApiPaths.ID) id: Int,
        @Query(ApiQueries.DATE) date: String,
        @Query(ApiQueries.PAGE) page: Int = -1,
        ): TimeSlotsListResponse

}
