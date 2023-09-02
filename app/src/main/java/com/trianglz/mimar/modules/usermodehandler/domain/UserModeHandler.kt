package com.trianglz.mimar.modules.usermodehandler.domain

import com.trianglz.core.domain.model.GlobalCoroutineScopeHolder
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Singleton for user type handling and to prompt for upgrading either from guest to free user
 * and/or from free user to premium user
 * Already injected in [com.trianglz.mystud.commons.presentation.base.BaseFragment] and
 * [com.trianglz.mystud.modules.home.presentation.HomeActivity]
 *
 * In home activity it's injected to show the UI updates (Login Dialog) from a single place.
 * And in the Base Fragment to handle clicks or navigation functions for example.
 *
 * Can also be injected into the view models if needed.
 *
 * To get started, in most cases, only [isGuest], [hasProMembership] would be sufficient.
 */
@Singleton
class UserModeHandler @Inject constructor(
    private val scopeHolder: GlobalCoroutineScopeHolder,
    private val userRepo: UserRepository,
) {

    private val _onGuestUserRejected = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    private val _onFreeUserRejected = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    private val _showAddServiceToCartValidationDialog =
        MutableSharedFlow<Pair<Int, String>>(0, 1, BufferOverflow.DROP_OLDEST)

    fun getOnGuestRejectedEvents() = _onGuestUserRejected.asSharedFlow()
    fun getOnFreeUserRejectedEvents() = _onFreeUserRejected.asSharedFlow()
    fun showAddServiceToCartValidationDialogEvents() = _showAddServiceToCartValidationDialog.asSharedFlow()

    /**
     * Please keep in mind that this coroutine scope is tied to the lifecycle of the whole app
     * If you are not sure if it's safe or not to use it, use [isGuest] instead
     *
     * @param action to do once we're sure user is not guest
     */
    fun doIfNotGuest(action: () -> Unit) {
        scopeHolder.scope.launch {
            if (userRepo.checkIfUserLoggedIn()) {
                action()
            } else {
                _onGuestUserRejected.tryEmit(Unit)
            }
        }
    }

    /**
     * Returns true if user is a guest. Once true, [onGuestUserRejected] is triggered to update the
     * UI accordingly
     */
    suspend fun isGuest(notify: Boolean = true): Boolean {
        return if (userRepo.checkIfUserLoggedIn()) {
            false
        } else {
            if (notify) _onGuestUserRejected.tryEmit(Unit)
            true
        }
    }
    fun notifyClearCart(newServiceId: Int, branchName: String) {
        _showAddServiceToCartValidationDialog.tryEmit(Pair(newServiceId, branchName))
    }

    /**
     * the thread blocking version of [isGuest]. Please use only if there is no way you can use
     * suspending functions from your code. i.e creating coroutines is not possible (Like the on
     * bottom navigation bar menu selection)
     *
     */
    fun isGuestBlocking(notify: Boolean = true) = runBlocking { isGuest(notify) }

}