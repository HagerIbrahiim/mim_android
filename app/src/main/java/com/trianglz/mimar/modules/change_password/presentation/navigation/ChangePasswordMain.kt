package com.trianglz.mimar.modules.change_password.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.change_password.presentation.ChangePasswordScreen
import com.trianglz.mimar.modules.change_password.presentation.ChangePasswordViewModel
import com.trianglz.mimar.modules.change_password.presentation.model.ChangePasswordNavArgs

@Composable
@Destination(navArgsDelegate = ChangePasswordNavArgs::class)
@MainGraph
fun ChangePasswordMainScreen(navigator: DestinationsNavigator,
                       viewModel: ChangePasswordViewModel = hiltViewModel()) {
    ChangePasswordScreen(navigator, viewModel)
}