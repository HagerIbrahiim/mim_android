package com.trianglz.mimar.common.presentation.base

import com.trianglz.core.domain.helper.SingleRunner
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.core.domain.usecase.BaseUpdatesUseCase
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.ToggleBranchFavouritesUseCase
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.cart.domain.usecase.AddServiceToCartUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.RemoveServiceFromCartUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import javax.inject.Inject

abstract class GeneralUpdatesViewModel<Event : BaseEvent, ViewState : BaseViewState, State : BaseState>(
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    vararg baseUpdatesUseCase: BaseUpdatesUseCase<*>
) : MimarBaseViewModel<Event, ViewState, State>(getUserUpdatesUseCase) {

    private val runner = SingleRunner()

    @Inject
    lateinit var toggleUserFavouritesUseCase: ToggleBranchFavouritesUseCase

    @Inject
    lateinit var addServiceToCart: AddServiceToCartUseCase

    @Inject
    lateinit var removeServiceFromCartUseCase: RemoveServiceFromCartUseCase

    init {
        baseUpdatesUseCase.forEach {
            launchCoroutine {
                it.execute().collect {
                    updateItem(it)
                }
            }
        }

    }

    fun toggleUpdatableItem(item: BaseUpdatableItem, toggle: Boolean = true) {
        launchCoroutine {
            setLoading()
            runner.afterPrevious {
                when (item) {
                    is BranchDomainModel -> {
                        toggleUserFavouritesUseCase.execute(item.uniqueId, toggle)
                    }

                    is ServiceDomainModel ->{
                        if (!toggle) {
                            addServiceToCart.execute(item)
                        } else {
                            item.serviceIdInCart?.let { removeServiceFromCartUseCase.execute(it) }
                        }
                    }

                    else -> {}
                }
            }
            setDoneLoading()
        }
    }

    abstract fun updateItem(item: BaseUpdatableItem)
}