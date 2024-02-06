package xyz.poolp.bigmac.util

import android.content.Context

fun Context.loadJSONFromAssets(fileName: String): String {
    return assets.open(fileName).bufferedReader().use { reader ->
        reader.readText()
    }
}