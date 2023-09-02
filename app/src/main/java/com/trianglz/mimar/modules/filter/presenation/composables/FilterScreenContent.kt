package com.trianglz.mimar.modules.filter.presenation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.trianglz.core.domain.extensions.toIsoFormat
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.domain.extention.toFormattedHourMinutes12Format
import com.trianglz.mimar.common.domain.extention.toHourMinutes24Format
import com.trianglz.mimar.common.presentation.compose_views.SideScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun FilterScreenContent(
    availabilityTimeCount: @Composable () -> Int,
    ratingCount: @Composable () -> Int,
    selectedAvailabilityTime: () -> Pair<LocalDate?, LocalTime?>?,
    specialtiesListCount: @Composable () -> Int?,
    specialitiesSelectedText: @Composable () -> String,
    offeredLocationCount: @Composable () -> Int?,
    offeredLocationSelectedText: @Composable () -> String,
    selectedGenderCount: @Composable () -> Int?,
    genderSelectedText: @Composable () ->String,
    ratingText: @Composable  () -> String,
    onBackClicked: ()-> Unit,
    onSpecialtiesClicked: ()-> Unit,
    onSelectGenderClicked: ()-> Unit,
    onSelectRatingClicked: ()-> Unit,
    onSelectAvailabilityTimeClicked: ()-> Unit,
    onSelectOfferedLocationsClicked: ()-> Unit,
    onResetClicked: () -> Unit,
    onApplyClicked: () -> Unit,

    ) {


    val spaceBetweenItemsDouble : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 2
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .setStatusBarPadding()
            .padding(bottom = MaterialTheme.dimens.bottomNavigationHeight)
    ) {

        Column {

            Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.screenGuideXLarge))

            HeaderShadow(dividerColor = Iron) { _ ->
                Column {
                    SideScreenHeader(
                        { stringResource(id = R.string.filter) },
                        modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
                        closeClicked = onBackClicked
                    )

                    Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.spaceBetweenItemsMedium ))

                }

            }



            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)) {

                Spacer(modifier = Modifier.padding(top = spaceBetweenItemsDouble()))

                ClickableDropDownField(
                    label = { R.string.specialties },
                    unSelectedText = { R.string.select_specialties },
                    selectedText = specialitiesSelectedText ,
                    itemsCount = specialtiesListCount  ,
                    onItemClick = onSpecialtiesClicked,
                )

                Spacer(modifier = Modifier.padding(top = spaceBetweenItemsDouble()))


                ClickableDropDownField(
                    label = { R.string.serviced_genders },
                    unSelectedText = { R.string.select_serviced_genders},
                    selectedText = genderSelectedText ,
                    itemsCount =  selectedGenderCount,
                    onItemClick = onSelectGenderClicked,
                )


                Spacer(modifier = Modifier.padding(top = spaceBetweenItemsDouble()))


                ClickableDropDownField(
                    label = { R.string.offered_locations },
                    unSelectedText = { R.string.select_offered_locations},
                    selectedText = offeredLocationSelectedText ,
                    itemsCount = offeredLocationCount,
                    onItemClick = onSelectOfferedLocationsClicked,
                )

                Spacer(modifier = Modifier.padding(top = spaceBetweenItemsDouble()))


                ClickableDropDownField(
                    label = { R.string.availability_time },
                    unSelectedText = { R.string.select_availability_time },
                    selectedText = { "${selectedAvailabilityTime()?.first?.toIsoFormat()}  ${selectedAvailabilityTime()?.second?.toFormattedHourMinutes12Format() ?: ""}" },
                    itemsCount =  availabilityTimeCount ,
                    onItemClick = onSelectAvailabilityTimeClicked,
                )

                Spacer(modifier = Modifier.padding(top = spaceBetweenItemsDouble()))


                ClickableDropDownField(
                    label = { R.string.rating },
                    unSelectedText = { R.string.select_rating },
                    selectedText = ratingText,
                    itemsCount = ratingCount ,
                    onItemClick = onSelectRatingClicked,
                )

            }

        }
        FilterButtonsRow(
            modifier = { Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault) },
            onResetClicked,
            onApplyClicked
        )
    }


}