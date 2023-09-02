package com.trianglz.mimar.common.presentation.base

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

abstract class MimarBaseViewModel<Event : BaseEvent, ViewState : BaseViewState, State : BaseState>(getUserUpdatesUseCase: GetUserUpdatesUseCase) : BaseMVIViewModel<Event, ViewState, State>() {

    private val user = getUserUpdatesUseCase.execute().map { it.toUIModel() }
    val notificationCount = mutableStateOf(0)
    val cartCount = mutableStateOf(0)

    protected fun startListenForUserUpdates() {
        launchCoroutine {
            user.collectLatest {
                userUpdates(it)
                cartCount.value = it.cart?.cartBranchServicesCount?.value ?: 0
                notificationCount.value = it.unseenNotificationsCount ?: 0
            }
        }
    }

    protected open fun userUpdates(user: UserUIModel) {

    }
}