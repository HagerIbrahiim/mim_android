package com.trianglz.mimar.modules.account.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core.presentation.extensions.toActivityAsNewTask
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.extensions.*
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.account.presentation.composables.AccountScreenContent
import com.trianglz.mimar.modules.account.presentation.contract.AccountEvent
import com.trianglz.mimar.modules.account.presentation.contract.AccountState
import com.trianglz.mimar.modules.account.presentation.contract.AccountViewState
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.destinations.AddressesListMainDestination
import com.trianglz.mimar.modules.destinations.ChangePasswordMainScreenDestination
import com.trianglz.mimar.modules.destinations.SetupProfileMainScreenDestination
import com.trianglz.mimar.modules.home.presentation.HomeActivity

@MainGraph(start = false)
@Destination
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )

        onDispose {}
    }
    //endregion
    val context = LocalContext.current
    val activity = context.getActivity<HomeActivity>()


    val viewStates = remember {
        viewModel.viewStates ?: AccountViewState()
    }

    val list = remember {
        viewStates.list
    }

    AccountScreenContent(list)

    GeneralObservers<AccountState, AccountViewModel>(viewModel = viewModel) {
        when (it) {
            AccountState.OpenAddresses -> {
                navigator.navigate(AddressesListMainDestination)
            }
            AccountState.OpenChangePassword -> {
                navigator.navigate(ChangePasswordMainScreenDestination(true))
            }
            is AccountState.OpenContactViaMail -> {
                context.sendMailIntent(email = it.mail)
            }
            is AccountState.OpenContactViaPhone -> {
                context.openDial(it.phone)
            }
            AccountState.OpenEditProfile -> {
                navigator.navigate(SetupProfileMainScreenDestination(true))
            }
            AccountState.OpenLogin -> {
                activity?.toActivityAsNewTask<AuthenticationActivity>()
            }

            AccountState.ShowDeleteAccountDialog -> {
                context.showDeleteAccountWarning{
                    viewModel.setEvent(AccountEvent.ConfirmDeleteAccountClicked)
                }
            }
            AccountState.ShowLanguageDialog -> {
                activity?.apply {
                    val otherLang = Locales.values().find { it.code != currentLocale.code }
                        ?: Locales.ENGLISH
                    context.showLanguageDialog(otherLang){
                        updateLocale(otherLang)
                    }
                }
            }
            AccountState.ShowLogoutDialog -> {
                context.showLogOutWarning {
                    viewModel.setEvent(AccountEvent.ConfirmLoggingOutClicked)
                }
            }
            AccountState.OpenHome -> {
                val data = listOf(
                    Pair(HomeActivity.SHOW_DELETE_ACCOUNT_MSG, true),
                )
                activity?.toActivityAsNewTaskWithParams<HomeActivity>(*data.toTypedArray())
            }
        }
    }
    HandleLoadingStateObserver(viewModel = viewModel) {
    }
}