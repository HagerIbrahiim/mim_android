package com.trianglz.mimar.modules.notification.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationRequestModel
import com.trianglz.mimar.modules.notification.data.retrofit.response.NotificationsListResponseModel
import com.trianglz.mimar.modules.notification.data.retrofit.response.SetNotificationSeenResponse
import com.trianglz.mimar.modules.notification.data.utils.NotificationsConstants
import retrofit2.http.*

interface NotificationService {
    @GET(ApiPaths.NOTIFICATIONS)
    suspend fun getUpdatesApiService(
        @Query(ApiQueries.PAGE) page: Int,
        @Query(ApiQueries.ITEMS) perPage: Int = 10,
    ): NotificationsListResponseModel

    @PATCH(ApiPaths.SET_NOTIFICATION_SEEN)
    suspend fun updateNotification(
        @Path(ApiPaths.ID) id: Int,
    ): SetNotificationSeenResponse

    @Headers(NotificationsConstants.FIREBASE_AUTH)
    @POST
    suspend fun sendNotification(
        @Url url: String,
        @Body messageNotificationModel: NotificationRequestModel
    )

}