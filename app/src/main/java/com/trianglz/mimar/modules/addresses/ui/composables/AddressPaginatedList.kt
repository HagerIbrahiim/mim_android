package com.trianglz.mimar.modules.addresses.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetTopSection
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.model.AddressStickyHeader
import com.trianglz.mimar.modules.addresses_list.presentation.source.AddressesListSource
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList

@Composable
fun AddressPaginatedList(
    source: () -> AddressesListSource,
    listBottomPadding: @Composable () -> Dp,
    listTopPadding: @Composable () -> Dp = { MaterialTheme.dimens.screenGuideXSmall.plus(2.dp) } ,
    fillMaxSize: Boolean = true,
    showEditBtn: Boolean = true,
    showDeleteBtn: Boolean = true,
    swipeEnabled: () -> Boolean = { true },
    filterByBranchIdInCart: () -> Boolean? = { null},
    addNewAddressButtonClicked: () -> Unit = {},
    stickyTitleBackClicked: () -> Unit = {},
    onChangeDefaultAddressClicked: (CustomerAddressUIModel) -> Unit = {},
    onEditAddressClicked: (CustomerAddressUIModel) -> Unit = {},
    onDeleteAddressClicked: (Int) -> Unit = {},
) {

    val lazyListModifier = remember {
        if(fillMaxSize) Modifier.fillMaxSize() else Modifier
    }


    ComposePaginatedList(
        lazyListModifier = Modifier.padding(bottom = MaterialTheme.dimens.screenGuideDefault).then(lazyListModifier),
        spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
        swipeEnabled= swipeEnabled(),
        paginatedListSource = source(),
        contentPadding = PaddingValues(
            top = listTopPadding(),
            start = MaterialTheme.dimens.screenGuideDefault,
            end = MaterialTheme.dimens.screenGuideDefault,
            bottom =  listBottomPadding()
        ),
        listItem = { _, item ->
            when(item){
                is CustomerAddressUIModel ->{
                    AddressItem(
                        address = { item },
                        showDeleteBtn = showDeleteBtn,
                        showEditBtn = showEditBtn,
                        filterByBranchIdInCart = filterByBranchIdInCart,
                        onDeleteAddressClicked = onDeleteAddressClicked,
                        onEditAddressClicked = onEditAddressClicked,
                        addressItemClicked = onChangeDefaultAddressClicked,
                    )
                }
                is AddressStickyHeader -> {
                    BottomSheetTopSection(
                        addHorizontalSpacing = false,
                        title = { StringWrapper(R.string.address) },
                        onBackButtonClicked = stickyTitleBackClicked
                    )
                }
            }

        },
        emptyListPlaceholder = {

            if(fillMaxSize) {
                MimarPlaceholder(
                    modifier = { Modifier.fillParentMaxSize() },
                    animationFile =  R.raw.addresses ,
                    titleFirstText = { R.string.saved },
                    titleSecondText = { R.string.addresses },
                    descriptionText = { StringWrapper(R.string.if_you_prefer_having_your_appointments) },
                    buttonText = { R.string.add_new_address },
                    onButtonClicked = addNewAddressButtonClicked
                )
            }
        },
    )

}