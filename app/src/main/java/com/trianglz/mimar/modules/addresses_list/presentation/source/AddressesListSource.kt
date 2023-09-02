package com.trianglz.mimar.modules.addresses_list.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.addresses.domain.usecase.GetAddressesListUseCase
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.model.AddressStickyHeader
import com.trianglz.mimar.modules.addresses_list.presentation.model.BaseAddressModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddressesListSource @Inject constructor(
    private val getAddressesListUseCase: GetAddressesListUseCase,
    private val getUserUpdatesUseCase: GetUserUpdatesUseCase,
) : ComposePaginatedListDataSource<BaseAddressModel>(
    autoInit = false,
    shimmerList = CustomerAddressUIModel.getShimmerList()
) {
    var showHeader: Boolean = false
    var addCurrentLocationToAddresses: Boolean = false
    var selectedLocationId: Int? = null
    var filterByBranchIdInCart: Boolean? = null


    override suspend fun getPage(page: Int, perPage: Int): List<BaseAddressModel> {
        val addressWithStickyModel: ArrayList<BaseAddressModel> = ArrayList()
        val user = getUserUpdatesUseCase.execute().first()
        val list = getAddressesListUseCase.execute(page, perPage, filterByBranchIdInCart).map {
            it.toUI(
                isChecked = if (selectedLocationId != null) it.id == selectedLocationId else it.isDefault,
            )
        }.toMutableList()
        if (page == 1 ) {
            if(showHeader) addressWithStickyModel.add(AddressStickyHeader(true))
            if(addCurrentLocationToAddresses)
                list.add(
                    0,
                    CustomerAddressUIModel.getCurrentLocationItem(
                        isChecked = user.isSetCurrentLocation|| list.isEmpty(),
                    )
                )
        }

        addressWithStickyModel.addAll(list)
        return addressWithStickyModel
    }
}
