package com.trianglz.mimar.common.presentation.fileProvider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.trianglz.mimar.R
import java.io.File


class ComposeFileProvider : FileProvider(R.xml.file_paths) {

    companion object {
        /**
         * This method returns pair of two URIs:
         * First URI: is called (content URI) used for launching and picking image from the the camera
         * Second URI: is called (file URI) used for uploading the image
         */
        fun getImageUris(context: Context): Pair<Uri, Uri> {

            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val timeStamp = System.currentTimeMillis().toString()

            val file = File.createTempFile(
                timeStamp,
                ".jpg",
                directory
            )

            val authority = context.packageName + ".FileProvider"

            return Pair(
                getUriForFile(
                    context,
                    authority,
                    file,
                ),
                file.toUri()
            )
        }
    }
}