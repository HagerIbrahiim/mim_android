package com.trianglz.mimar.common.presentation.extensions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.extensions.showMaterialDialog
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.home.presentation.contracts.HomeActivityEvent
import java.util.*


fun Context.showLogoutDialog(action: () -> Unit) {
    showMaterialDialog(
        getString(R.string.logout),

        getString(R.string.are_you_sure_to_logout),
        getString(R.string.yes),
        getString(R.string.no),
        {
            action()
        },
        null
    )
}

fun Context.showLanguageDialog(otherLang: Locales, updateLanguage: () -> Unit) {


    val dialogLanguageString =
        if (otherLang == Locales.ARABIC) getString(R.string.arabic_language) else getString(R.string.english_language)
    showMaterialDialog(
        getString(R.string.change_language),
        getString(R.string.are_you_sure_change_language, dialogLanguageString),
        getString(R.string.yes),
        getString(R.string.no),
        {
            updateLanguage()
        },
        null
    )
}
fun Context.showGuestUserDialog(action: () -> Unit) {
    showMaterialDialog(
        getString(R.string.user_not_logged_in),

        getString(R.string.create_an_account_to),
        getString(R.string.create_account),
        getString(com.trianglz.core.R.string.cancel),
        {
            action()
        },
        null,
        isCancellable = false
    )
}
fun Context.showAddToCartValidationDialog(branchName: String, action: () -> Unit) {
    showMaterialDialog(
        getString(R.string.app_name),

        getString(R.string.adding_this_service_will_clear_the_existing_cart_from_branch, branchName),
        getString(R.string.proceed),
        getString(com.trianglz.core.R.string.cancel),
        {
            action()
        },
        null,
        isCancellable = false
    )
}
fun Context.showRemoveServiceFromCartConfirmationDialog(action: () -> Unit) {
    showMaterialDialog(
        getString(R.string.remove_service),

        getString(R.string.do_you_want_to_remove_this_service_from_the_cart),
        getString(com.trianglz.core.R.string.OK),
        getString(com.trianglz.core.R.string.cancel),
        {
            action()
        },
        null,
        isCancellable = false
    )
}

fun Context.checkIfLocationServicesIsEnabled(resultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>, action: () -> Unit) {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =  LocationManagerCompat.isLocationEnabled(locationManager)
    if(!isLocationEnabled){
        showMaterialDialog(
            getString(R.string.location_is_not_active),

            getString(R.string.enable_location_services),
            getString(R.string.yes),
            getString(R.string.no),
            {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                resultLauncher.launch(intent)
            },
            null
        )
    }
    else {
        action()
    }

}

fun Context.checkIfLocationGranted(): Boolean {
    var result = false
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =  LocationManagerCompat.isLocationEnabled(locationManager)
    if (isLocationEnabled) {
        val permissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (permissionGranted) {
            result = true
        }
    }
    return result

}

fun Context.openGoogleMaps(title: String,lat: Double, lng: Double){
    val uri = "http://maps.google.com/maps?q=loc:$lat,$lng ($title)"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

fun Context.showLogOutWarning(positiveOptionClicked: () -> Unit) {
        showMaterialDialog(
            title = getString(R.string.logout),
            content = getString(R.string.are_you_sure_you_want_to_log_out),
            positiveMessage = getString(R.string.logout),
            negativeMessage = getString(R.string.cancel),
            positive = positiveOptionClicked,
            negative = {},
            isCancellable = true
        )
}


fun Context.showDeleteAccountWarning(positiveOptionClicked: () -> Unit) {

        showMaterialDialog(
            title = getString(R.string.delete_account),
            content = getString(R.string.are_you_sure_you_want_to_delete_your_account),
            positiveMessage = getString(R.string.delete),
            negativeMessage = getString(R.string.cancel),
            positive = positiveOptionClicked,
            negative = {},
            isCancellable = true
        )


}

private fun Context.showContactUsDialog(phoneNumClicked : ()-> Unit, emailClicked: () -> Unit){

    showMaterialDialog(
        getString(R.string.contact_us),
        getString(R.string.contact_us),
        getString(com.trianglz.core.R.string.email),
        getString(com.trianglz.core.R.string.phone_number),
        emailClicked,
        phoneNumClicked,
    )
}

fun Context.openDial(phoneNumber: String){
    val u = Uri.parse("tel:$phoneNumber")
    val i = Intent(Intent.ACTION_DIAL, u)
    startActivity(i)
}

fun Context.sendMailIntent(email: String, subject: String?="", body: String?="") {
    val selectorIntent = Intent(Intent.ACTION_SENDTO)
    selectorIntent.data = Uri.parse("mailto:")

    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    emailIntent.putExtra(Intent.EXTRA_TEXT, body)
    emailIntent.selector = selectorIntent

    startActivity(Intent.createChooser(emailIntent, "Send email..."))
}

fun Context.listenToInternetConnectivity(onNetworkChanged: (Boolean) -> Unit){
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            onNetworkChanged(true)
        }

        override fun onLost(network: Network) {
            onNetworkChanged(false)
        }

    })
}