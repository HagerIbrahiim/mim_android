package com.trianglz.mimar.modules.branch_reviews.presentation

import androidx.lifecycle.SavedStateHandle
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsEvent
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsState
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsViewState
import com.trianglz.mimar.modules.branch_reviews.presentation.source.BranchReviewsSource
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchDetailsUseCase
import com.trianglz.mimar.modules.destinations.BranchReviewsScreenDestination
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BranchReviewsViewModel @Inject constructor(
    val source: BranchReviewsSource,
    private val savedStateHandle: SavedStateHandle,
    private val getBranchDetailsUseCase: GetBranchDetailsUseCase,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
) : MimarBaseViewModel<BranchReviewsEvent, BranchReviewsViewState, BranchReviewsState>(getUserUpdatesUseCase) {

    init {
        startListenForUserUpdates()
        saveDataFromNavArg()
    }

    private fun fetchData() {
        getBranchDetails()
        getReviews()
    }

    private fun getBranchDetails() {
        launchCoroutine {
            setLoadingWithShimmer()
            viewStates?.isRefreshing?.value = true
            val branch = getBranchDetailsUseCase.execute(viewStates?.branchId?.value ?: -1)
            viewStates?.branchName?.value = branch.name
            viewStates?.rating?.value =  branch.rating.toString()
            viewStates?.numOfReviews?.value = branch.reviewsCount.toString()
            viewStates?.isRefreshing?.value = false
            setDoneLoading()
        }
    }

    private fun getReviews() {
        source.branchId = viewStates?.branchId?.value
        source.refreshAll()
    }

    private fun saveDataFromNavArg() {
        viewStates?.branchId?.value = BranchReviewsScreenDestination.argsFrom(savedStateHandle).branchId
        fetchData()

    }
    override fun handleEvents(event: BranchReviewsEvent) {
        when (event) {

            BranchReviewsEvent.RefreshScreen -> {
                fetchData()
            }
        }
    }

    override fun createInitialViewState(): BranchReviewsViewState {
        return BranchReviewsViewState()
    }
}