package com.part2.a3dify.app_entry

import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

internal fun MainFragment.checkPermission() {
    when {
        // utility class provided by the Android Support Library / Androidx
        context?.let {
            ContextCompat.checkSelfPermission(
                it, // context of the activity in which the fragment is hosted in
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } == PackageManager.PERMISSION_GRANTED -> {
            loadImage()
        }

        // already tried once to get permission -> show UI Rationale before asking permission
        shouldShowRequestPermissionRationale(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) -> {
            showPermissionInfoDialog()
        }
        else -> {
            // Reaching here when :
            // 1. never tried to reach the gallery
            requestReadExternalStorage()
        }
    }
}


// Already requested for permission, but rejected -> Double checking using dialog
internal fun MainFragment.showPermissionInfoDialog() {
    context?.let {
        AlertDialog.Builder(it).apply {
            setMessage("To get image from storage, need READ permission for storage.")
            setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Ok") { dialogInterface, which ->
                dialogInterface.dismiss()

                if (which == DialogInterface.BUTTON_POSITIVE) {
                    requestReadExternalStorage()
                }
            }
        }.show()
    }
}

internal fun MainFragment.requestReadExternalStorage() {
    requestReadExternalStorageLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
}