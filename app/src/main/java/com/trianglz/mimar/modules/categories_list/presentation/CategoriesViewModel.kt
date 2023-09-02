package com.trianglz.mimar.modules.categories_list.presentation


import com.trianglz.core.domain.helper.ControlledRunner
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesEvent
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesState
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesViewState
import com.trianglz.mimar.modules.categories_list.presentation.source.CategoriesSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val source: CategoriesSource,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase
) : MimarBaseViewModel<CategoriesEvent, CategoriesViewState, CategoriesState>(getUserUpdatesUseCase) {

    private val controlledRunner = ControlledRunner<Unit>()

    init {
        startListenForUserUpdates()
        initialize()
        getData()
    }

    private fun initialize() {
        viewStates?.scource?.value = source
    }


    private fun getData() {
        resetViewStates()
        launchCoroutine {
            setLoadingWithShimmer()
            getCategories()
            setDoneLoading()
        }
    }

    private suspend fun getCategories() {
        controlledRunner.cancelPreviousThenRun {
            source.refreshAll()
        }

    }

    override fun handleEvents(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.OnCategoryClicked -> {
                setState { CategoriesState.OpenCategory(event.id) }
            }
            CategoriesEvent.RefreshScreen -> {
                getData()
            }
        }
    }

    override fun createInitialState(): CategoriesState {
        return CategoriesState.Idle
    }

    override fun createInitialViewState(): CategoriesViewState {
        return CategoriesViewState()
    }
}