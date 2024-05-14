package com.intern.conjob.arch.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionUtils {

    fun checkImagePermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            checkStoragePermission(activity)
        } else {
             if (activity.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    Constants.READ_MEDIA_IMAGES_CODE
                )
                false
            } else {
                true
            }
        }
    }

    fun checkVideoPermission(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            checkStoragePermission(activity)
        } else {
            if (activity.checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_MEDIA_VIDEO),
                    Constants.READ_MEDIA_VIDEO_CODE
                )
                false
            } else {
                true
            }
        }
    }

    private fun checkStoragePermission(activity: Activity): Boolean {
        return if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.READ_EXTERNAL_STORAGE_CODE
            )
            false
        } else {
            true
        }
    }
}