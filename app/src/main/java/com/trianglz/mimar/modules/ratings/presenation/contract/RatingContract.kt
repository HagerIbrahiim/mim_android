package com.trianglz.mimar.modules.ratings.presenation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.ratings.domain.model.EmployeeRatingDomainModel
import com.trianglz.mimar.modules.ratings.presenation.model.RatingAppointmentUIModel
import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt


/// Events that user performed
sealed class RatingEvent : BaseEvent {
    data class SetSheetData(val sheetData: ReviewAppointmentSheetData): RatingEvent()
    object SubmitReviewBtnClicked: RatingEvent()
    object CheckContentValidation: RatingEvent()
}

sealed class RatingState : BaseState {

    object RefreshAppointment: RatingState()

}

data class RatingViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val list:  MutableState<List<RatingAppointmentUIModel>>? = mutableStateOf(listOf()),
    val isContentValid: MutableState<Boolean> = mutableStateOf(false),
    var feedback: TextFieldState = TextFieldState(),
    var sheetData: MutableState<ReviewAppointmentSheetData?> = mutableStateOf(null),

    ) : BaseViewState{

    val submittedEmployeesRatingList get() : MutableState< List<EmployeeRatingDomainModel>?> = mutableStateOf(
        list?.value?.map {
            EmployeeRatingDomainModel(it.id,it.rating.value.roundToInt())
        }?.filter { it.rating > 0}?.filter { it.employeeId != -1 }
    )
}