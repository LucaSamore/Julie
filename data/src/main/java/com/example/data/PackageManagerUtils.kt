package com.example.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import arrow.core.Either
import javax.inject.Inject

class PackageManagerUtils @Inject constructor(context: Context) {
    private val packageManager = context.packageManager

    fun getInstalledAppPackageNames(): List<String> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolvedInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.queryIntentActivities(
                    mainIntent,
                    PackageManager.ResolveInfoFlags.of(0L)
                )
            } else {
                packageManager.queryIntentActivities(mainIntent, 0)
            }
        return resolvedInfo.map { it.activityInfo.packageName }.distinct()
    }

    fun getAppNameFromPackageName(packageName: String): String {
        return Either.catch { packageManager.getApplicationInfo(packageName, 0) }
            .fold(
                { packageName },
                { packageManager.getApplicationLabel(it).toString() },
            )
    }

    fun getAppIcon(packageName: String): Either<String, Drawable> {
        return Either.catch { packageManager.getApplicationIcon(packageName) }
            .mapLeft { "Not Found" }
    }
}
