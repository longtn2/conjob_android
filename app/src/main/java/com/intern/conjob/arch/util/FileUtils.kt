package com.intern.conjob.arch.util

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log

object FileUtils {
    fun getPath(uri: Uri, context: Context): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null

        if (DocumentsContract.isDocumentUri(context, uri)) {
            when (uri.authority) {
                Constants.IS_DOWNLOADS_DOCUMENT -> {
                    val id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:".toRegex(), "")
                    }
                    uri = ContentUris.withAppendedId(
                        Uri.parse(Constants.DOWNLOADS_URI), java.lang.Long.valueOf(id)
                    )
                }

                Constants.IS_MEDIA_DOCUMENT -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    when (split[0]) {
                        Constants.MEDIA_IMAGE -> uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        Constants.MEDIA_VIDEO -> uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(
                        split[1]
                    )
                }

                Constants.IS_EXTERNAL_STORAGE_DOCUMENT -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            }
        }

        return getPathFromOther(context, uri, selection, selectionArgs)
    }

    private fun getPathFromOther(
        context: Context,
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        if (Constants.CONTENT.equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA
            )
            try {
                context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                    .use { cursor ->
                        if (cursor != null && cursor.moveToFirst()) {
                            val columnIndex =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            return cursor.getString(columnIndex)
                        }
                    }
            } catch (e: Exception) {
                Log.i("Error", e.toString())
            }
        } else if (Constants.FILE.equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }
}
