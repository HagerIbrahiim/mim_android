package com.trianglz.mimar.common.presentation.helper

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


fun ManagedActivityResultLauncher<Intent, ActivityResult>.startGooglePlacesActivity(context: Context) {

    val intent = Autocomplete.IntentBuilder(
        AutocompleteActivityMode.FULLSCREEN,
        listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
    ).build(context)

    launch(intent)

}