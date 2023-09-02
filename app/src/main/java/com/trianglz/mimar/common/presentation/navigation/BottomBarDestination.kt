package com.trianglz.mimar.common.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.destinations.*


enum class BottomBarDestinations(
    val direction:  TypedDestination<*>,
    @StringRes val title: Int,
    @DrawableRes val unSelectedIconId: Int,
    @DrawableRes val selectedIconId: Int,
    val route: String,
    val showToGuestUser:Boolean,
) {

    UserHome(
        direction = UserHomeScreenDestination,
        title = R.string.home,
        unSelectedIconId = R.drawable.ic_home_normal,
        selectedIconId = R.drawable.ic_home_pressed,
        route = "user_home_screen",
        showToGuestUser = true

    ),

    Explore(
        direction = DiscoverScreenDestination,
        title = R.string.explore,
        unSelectedIconId = R.drawable.ic_discover_normal,
        selectedIconId = R.drawable.ic_discover_pressed,
        route = "discover_screen",
        showToGuestUser = true
    ),

    Appointments(
        direction = AppointmentsScreenDestination,
        title = R.string.my_appointments,
        unSelectedIconId = R.drawable.ic_appointments_normal,
        selectedIconId = R.drawable.ic_appointments_pressed,
        route = "appointments_screen",
        showToGuestUser = false
    ),

    Favourites(
        direction = FavouritesScreenDestination,
        title = R.string.favourites,
        unSelectedIconId = R.drawable.ic_favourites_normal,
        selectedIconId = R.drawable.ic_favourites_pressed,
        route = "favourites_screen",
        showToGuestUser = false

    ),

    Account(
        direction = AccountScreenDestination,
        title = R.string.account,
        unSelectedIconId = R.drawable.ic_profile_normal,
        selectedIconId = R.drawable.ic_profile_pressed,
        route = "account_screen",
        showToGuestUser = false

    ),

}
