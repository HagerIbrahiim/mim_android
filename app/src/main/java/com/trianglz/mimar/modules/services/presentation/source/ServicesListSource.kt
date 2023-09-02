package com.trianglz.mimar.modules.services.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.services.domain.usecase.GetServicesListUseCase
import com.trianglz.mimar.modules.services.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import javax.inject.Inject

class ServicesListSource @Inject constructor(
    private val getServicesListUseCase: GetServicesListUseCase
) : ComposePaginatedListDataSource<ServiceUIModel>(
    shimmerList = ServiceUIModel.getShimmerList(),
    autoInit = false,
) {

    var onAddServiceToCartClicked: ((service: ServiceUIModel) -> Unit)? = null
    var onServiceItemClicked: ((service: ServiceUIModel) -> Unit)? = null
    var specialityId: Int? = null
    var branchId: Int? = null
    var searchString: String? = null
    var offeredLocation: String? = null
    var serviceType: ServiceType? = null

    override suspend fun getPage(page: Int, perPage: Int): List<ServiceUIModel> {
        return getServicesListUseCase.execute(
            page = page,
            perPage = perPage,
            specialityId = specialityId,
            searchString = searchString,
            branchId = branchId,
            offeredLocation = offeredLocation,
        ).map {
            it.toUI(type = serviceType,onAddServiceToCart = onAddServiceToCartClicked ?: {},
                onServiceItemClicked = onServiceItemClicked)
        }
    }


}