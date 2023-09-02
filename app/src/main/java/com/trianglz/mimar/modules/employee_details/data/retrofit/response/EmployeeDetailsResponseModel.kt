package com.trianglz.mimar.modules.employee_details.data.retrofit.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.employee_details.data.model.EmployeeDetailsDataModel

@Keep
data class EmployeeDetailsResponseModel(
    @SerializedName("employee")
    val employee: EmployeeDetailsDataModel,
) : SuccessMessageResponse()