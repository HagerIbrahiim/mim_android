package com.trianglz.mimar.modules.splash.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trianglz.core.presentation.enums.MessageDialogMode
import com.trianglz.core.presentation.extensions.makeFullScreen
import com.trianglz.core.presentation.extensions.observeEvent
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core_compose.presentation.base.BaseComposeMVIActivity
import com.trianglz.core_compose.presentation.composables.CustomLottieAnimation
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity.Companion.SCREEN_MODE
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationMode
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager.Companion.ACTION_ID
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager.Companion.CLICK_ACTION
import com.trianglz.mimar.modules.splash.presentation.contract.SplashActivityState
import com.trianglz.mimar.modules.splash.presentation.model.SplashResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseComposeMVIActivity<SplashViewModel>() {
    //region Members


    override fun initializeViewModel() {
        initializeViewModel(SplashViewModel::class.java)
    }

    private var animationFinished = false

    //    private var animationFinished = false
    private var splashAction: (() -> Unit)? = null
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        initObservers()
    }


    private fun initObservers() {
        observeEvent(viewModel.loadingState) {
            globalState.postValue(it)
        }
    }

    override fun handleStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.filterNotNull().collect {
                    when (it) {
                        is SplashActivityState.SplashResultState -> {
                            when (it.result) {
                                is SplashResult.AuthenticationResult -> {
                                    openAuthenticationWithMode(it.result.mode)
                                }
                                SplashResult.Error -> {
                                    // TODO Handle network error screen
//                                    toActivityAsNewTask<SplashErrorActivity>()
                                }
                                SplashResult.HomeResult -> {
                                    val list = listOf(
                                        Pair(
                                            ACTION_ID,
                                            intent.getStringExtra(ACTION_ID)
                                        ),
                                        Pair(
                                            CLICK_ACTION,
                                            intent.getStringExtra(CLICK_ACTION)
                                        ),
                                    )
                                    toActivityAsNewTaskWithParams<HomeActivity>(*list.toTypedArray())
                                }
                                else -> {}
                            }
                        }
                    }
                    super.handleStates()

                }
            }
        }

    }

    private fun openAuthenticationWithMode(mode: AuthenticationMode) {
        val list = listOf(
            Pair(SCREEN_MODE, mode),
            Pair(
                ACTION_ID,
                intent.getStringExtra(ACTION_ID)
            ),
            Pair(
                CLICK_ACTION,
                intent.getStringExtra(CLICK_ACTION)
            ),
        )
        toActivityAsNewTaskWithParams<AuthenticationActivity>(*list.toTypedArray())
    }


    override fun startActivity(intent: Intent?) {
        splashAction = {
            super.startActivity(intent)
            finish()
        }
        if (animationFinished) splashAction?.invoke()
    }

    override fun enableUpdateCheck(): Boolean {
        return false
    }

    @Composable
    override fun CustomLoadingDialog() {
    }

    @Composable
    override fun InitializeComposeScreen() {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.splash)
        )
        val progress by animateLottieCompositionAsState(composition)

        LaunchedEffect(key1 = progress){
            if (progress == 1.0f) {
                animationFinished = true
                splashAction?.invoke()
            }
        }

        LottieAnimation(
            composition,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            speed = 1.5F
        )

    }

    @Composable
    override fun CustomAlertDialog(message: String, mode: MessageDialogMode) {
//        TODO("Not yet implemented")
    }

    override fun allowAskNotificationPermission(): Boolean = false
}

