package com.trianglz.mimar.modules.ratings.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.ratings.data.data_source.RatingsRemoteDataSource
import com.trianglz.mimar.modules.ratings.data.mapper.toRequestModel
import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel
import com.trianglz.mimar.modules.ratings.domain.model.RatingDomainModel
import com.trianglz.mimar.modules.ratings.domain.model.SubmitAppointmentReviewDomainModel
import com.trianglz.mimar.modules.ratings.domain.repository.RatingsRepository
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    private val ratingsRemoteDataSource: RatingsRemoteDataSource
) : RatingsRepository {


    private val ratingsList = ArrayList<RatingDomainModel>()


    override fun submitFilterRating(rating: List<RatingDomainModel>) {
        val newList = SnapshotStateList<RatingDomainModel>()

        rating.forEach { item ->
            newList.add(item)
        }
        ratingsList.clear()
        ratingsList.addAll(newList)
    }

    override fun getFilterRatingsList(): List<RatingDomainModel> {

        return if(ratingsList.isEmpty()  ) {
            val  list  =ArrayList<RatingDomainModel>().apply {
                add(RatingDomainModel(0, 5F, StringWrapper(R.string.rating_five)))
                add(RatingDomainModel(1,4F, StringWrapper(R.string.rating_four_above),))
                add(RatingDomainModel(2,3F, StringWrapper(R.string.rating_three_above),))
                add(RatingDomainModel(3,2F, StringWrapper(R.string.rating_two_above),))
                add(RatingDomainModel(4,1F, StringWrapper(R.string.rating_one_above),))
            }

            ratingsList.addAll(list)
            list
        } else ratingsList


    }


    override  fun resetFilterRatingData() {
       ratingsList.clear()
    }

    override suspend fun submitAppointmentRating(rating: SubmitAppointmentReviewDomainModel) {
        ratingsRemoteDataSource.submitAppointmentRating(rating.toRequestModel())
    }


}