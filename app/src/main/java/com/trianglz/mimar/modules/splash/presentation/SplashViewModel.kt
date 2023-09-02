package com.trianglz.mimar.modules.splash.presentation


import androidx.lifecycle.viewModelScope
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.core.presentation.helper.ExceptionHandler
import com.trianglz.core.presentation.helper.getFcmTokenOrNull
import com.trianglz.core.presentation.model.AsyncState.Companion.notAuthorizedError
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationMode.*
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInState
import com.trianglz.mimar.modules.splash.presentation.contract.SplashActivityState
import com.trianglz.mimar.modules.splash.presentation.contract.SplashActivityState.SplashResultState
import com.trianglz.mimar.modules.splash.presentation.model.SplashResult
import com.trianglz.mimar.modules.splash.presentation.model.SplashResult.AuthenticationResult
import com.trianglz.mimar.modules.splash.presentation.model.SplashResult.HomeResult
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.CheckIfOnBoardingViewed
import com.trianglz.mimar.modules.user.domain.usecase.CheckUserIsLoggedInUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.UpdateUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @DeviceId private val deviceId: String,
    private val checkIfOnBoardingViewed: CheckIfOnBoardingViewed,
    private val checkUserIsLoggedIn: CheckUserIsLoggedInUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseMVIViewModel<BaseEvent, BaseViewState, SplashActivityState>() {
    //region Members

    private val updateUserExceptionHandler = exceptionHandler {
        if (ExceptionHandler.parse(it) == notAuthorizedError)
            setState { SplashResultState(AuthenticationResult(Login)) }
        else {
            launchCoroutine {
                setupNavigation(getUserUseCase.execute().toUIModel())
            }
        }
    }

    //endregion


    init {
        checkIfUserViewedTutorial()
    }

    //region Private API

    private fun checkIfUserViewedTutorial() {
        viewModelScope.launch(updateUserExceptionHandler) {
            if (checkIfOnBoardingViewed.execute())
                checkIfUserIsLoggedIn()
            else
                setState { SplashResultState(AuthenticationResult(OnBoarding)) }

        }

    }


    private fun checkIfUserIsLoggedIn() {
        launchCoroutine {

            when (checkUserIsLoggedIn.execute()) {
                true -> {
                    updateUser()
                }

                false ->
                    setState { SplashResultState(AuthenticationResult(Login)) }


            }
        }
    }

    private fun updateUser() {
        viewModelScope.launch(updateUserExceptionHandler) {
            val fcmToken = getFcmTokenOrNull()
            val user = updateUserUseCase.execute(
                UpdateUserDomainModel(
                    fcmToken = fcmToken,
                    deviceId = deviceId,
                )
            )

            setupNavigation(user.toUIModel())

        }

    }

    private fun setupNavigation(user: UserUIModel) {
        launchCoroutine {
            if(user.phoneNumber == null)
                setState { SplashResultState(AuthenticationResult(PhoneScreen))}
            else if (user.isPhoneVerified == false)
                setState { SplashResultState(AuthenticationResult(VerifyUser))}
            else if (user.mustChangePassword == true)
                setState { SplashResultState(AuthenticationResult(ChangePassword))}
            else if (user.isPhoneVerified == true && user.isProfileCompleted == false)
                setState { SplashResultState(AuthenticationResult(CompleteProfile))}
            else if (user.isProfileCompleted == true)
                setState { SplashResultState(HomeResult) }

        }
    }



    private fun showSplashError() {
        setState { SplashResultState(SplashResult.Error) }
    }

    override fun handleEvents(event: BaseEvent) {

    }

    override fun createInitialViewState(): BaseViewState? {
        return null
    }
    //endregion
}
