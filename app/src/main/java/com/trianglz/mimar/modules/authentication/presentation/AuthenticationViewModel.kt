package com.trianglz.mimar.modules.authentication.presentation

import androidx.lifecycle.viewModelScope
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.livedata.EventLiveData
import com.trianglz.core.presentation.livedata.EventMutableLiveData
import com.trianglz.mimar.modules.user.domain.usecase.LogOutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val logOutUserUseCase: LogOutUserUseCase,
    @DeviceId private val deviceId: String,
) : BaseMVIViewModel<BaseEvent, BaseViewState, BaseState>() {


    private val _openFirstScreen = EventMutableLiveData<Unit>()
    val openFirstScreen: EventLiveData<Unit>
        get() = _openFirstScreen

    private val deleteUserExceptionHandler = exceptionHandler {
        _openFirstScreen.postValue(Unit)
        setDoneLoading()
    }

    init {

    }


    //region Public API
    fun clearAllPref() {
        viewModelScope.launch(deleteUserExceptionHandler) {
            setLoading()
            logOutUserUseCase.execute(deviceId)
            _openFirstScreen.postValue(Unit)
            setDoneLoading()
        }
    }
    //endregion

    override fun handleEvents(event: BaseEvent) {
//        TODO("Not yet implemented")
    }

    override fun createInitialViewState(): BaseViewState? {
        return null
    }
}
