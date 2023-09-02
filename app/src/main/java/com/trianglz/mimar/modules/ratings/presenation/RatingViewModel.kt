package com.trianglz.mimar.modules.ratings.presenation

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.employee.presentation.mapper.toEmployeeRatingUIModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.ratings.domain.model.SubmitAppointmentReviewDomainModel
import com.trianglz.mimar.modules.ratings.domain.usecase.SubmitAppointmentRatingsUseCase
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingEvent
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingState
import com.trianglz.mimar.modules.ratings.presenation.contract.RatingViewState
import com.trianglz.mimar.modules.ratings.presenation.model.RatingAppointmentUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val submitAppointmentRatingsUseCase: SubmitAppointmentRatingsUseCase,
) : BaseMVIViewModel<RatingEvent, RatingViewState, RatingState>() {

    private val list get() = viewStates?.list?.value?: listOf()

    private val feedback get() = viewStates?.feedback?.textFieldValue?.value?.text


    init {
        observeFeedbackChanges()
    }
    override fun handleEvents(event: RatingEvent) {
        when (event) {
            RatingEvent.SubmitReviewBtnClicked -> {
                submitReview()
            }
            RatingEvent.CheckContentValidation -> {
                checkFeedbackValidation()
            }
            is RatingEvent.SetSheetData -> {
                viewStates?.sheetData?.value = event.sheetData
                checkFeedbackValidation()
                getAppointmentSheetData()
            }
        }
    }

    private fun getAppointmentSheetData() {
        val ratingList = viewStates?.sheetData?.value?.employeesList?.distinctBy { it.id }?.toMutableList()
        val list = mutableListOf<RatingAppointmentUIModel>()
        val checkAppointmentValidation = { setEvent(RatingEvent.CheckContentValidation) }

        list.add(
            RatingAppointmentUIModel(
                -1,
                viewStates?.sheetData?.value?.branchName ?: "",
                onValueChange = { checkAppointmentValidation() }
            )
        )
        list.addAll(ratingList?.map { it.toEmployeeRatingUIModel { checkAppointmentValidation() } } ?: listOf())
        viewStates?.list?.value = list
    }

    private fun submitReview(){

        val branchReview = list[0].rating.value.roundToInt()
        val review = SubmitAppointmentReviewDomainModel(
            rating = if(branchReview > 0) branchReview else null,
            feedback = feedback?.ifEmpty { null },
            appointmentId = viewStates?.sheetData?.value?.appointmentId,
            employeeRatingsAttributes = viewStates?.submittedEmployeesRatingList?.value?.filter { it.employeeId != -1 }?.ifEmpty { null })

        launchCoroutine {
            setLoading()
            submitAppointmentRatingsUseCase.execute(review)
            setDoneLoading()
            setState { RatingState.RefreshAppointment }
            _loadingState.postValue(AsyncState.SuccessWithMessage(StringWrapper(R.string.thanks_for_review)))

        }

    }

    private fun observeFeedbackChanges(){
        viewModelScope.launch(Dispatchers.IO) {
            snapshotFlow { feedback }
                .onEach {
                    setEvent(RatingEvent.CheckContentValidation)
                }
                .launchIn(viewModelScope)
        }
    }
    private fun checkFeedbackValidation(){
        val submittedEmployeesRatingListNotEmpty = viewStates?.submittedEmployeesRatingList?.value?.isNotEmpty() == true
        val feedbackNotEmpty = feedback?.isNotEmpty() == true
        val firstRatingValueGreaterThanZero = (list.firstOrNull()?.rating?.value ?: 0F) > 0F

        viewStates?.isContentValid?.value = submittedEmployeesRatingListNotEmpty || feedbackNotEmpty || firstRatingValueGreaterThanZero

    }

    override fun createInitialViewState(): RatingViewState {
        return RatingViewState()
    }

}
