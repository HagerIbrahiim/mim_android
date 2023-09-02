package com.trianglz.mimar.modules.setup_profile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.setup_profile.presentation.SetupProfileScreen
import com.trianglz.mimar.modules.setup_profile.presentation.SetupProfileViewModel
import com.trianglz.mimar.modules.setup_profile.presentation.model.SetupProfileNavArg

@Composable
@Destination(navArgsDelegate = SetupProfileNavArg::class)
@AuthGraph
fun SetupProfileAuthScreen(
    navigator: DestinationsNavigator,
    viewModel: SetupProfileViewModel = hiltViewModel()
) {
    SetupProfileScreen(navigator, viewModel)
}