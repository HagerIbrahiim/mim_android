package com.trianglz.mimar.common.presentation.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.trianglz.core.presentation.extensions.showPromptToOpenSettings
import com.trianglz.mimar.R
import java.text.SimpleDateFormat
import java.util.*


fun Activity.openDatePicker(
    selectedDay: String? = null,
    openCalenderWithCustomDate: Boolean = false,
    format: SimpleDateFormat,
    afterToday: Boolean = false,
    date: (String) -> Unit
) {
    (this as? AppCompatActivity)?.let {
        val dayInMillis  = selectedDay?.let {
            val dayInMilli = 86400000
            format.parse(selectedDay)?.time?.plus(dayInMilli)
        } ?: kotlin.run {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis
        }

        if (supportFragmentManager.findFragmentByTag("DatePicker")?.isAdded != true) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            val decThisYear = calendar.timeInMillis
            val dayInMilli = 86400000
            val constraintsBuilder = if (!afterToday) {
                CalendarConstraints.Builder()
                    .setEnd(decThisYear)
                    .setValidator(DateValidatorPointBackward.before(decThisYear - dayInMilli))
            } else {
                CalendarConstraints.Builder()
                    .setStart(decThisYear)
                    .setValidator(DateValidatorPointForward.now())
            }
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(
                    if(openCalenderWithCustomDate) dayInMillis else{
                    if (!afterToday)MaterialDatePicker.todayInUtcMilliseconds() - dayInMilli else MaterialDatePicker.todayInUtcMilliseconds()})
                .setTheme(R.style.DatePicker)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()


            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                utc.timeInMillis = it
                date.invoke(format.format(utc.time))
            }
        }
    }

}


fun Activity.checkCameraPermissions(action: () -> Unit) {
    var isShowPermissionDialog = false
    Dexter.withContext(this)
        .withPermissions(Manifest.permission.CAMERA)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        action()
                    } else if (report.deniedPermissionResponses.all { it.isPermanentlyDenied }) {
                        if (isShowPermissionDialog.not()) {
                            showPromptToOpenSettings()
                        }
                        isShowPermissionDialog = false
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                isShowPermissionDialog = true
                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {
        }
        .check()
}

// TODO: Add to core
fun Activity.isInternetAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities =
        connectivityManager.getNetworkCapabilities(network)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
        }
    }
    return false
}