package com.example.hack

import android.util.Base64
import androidx.annotation.IntDef
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


class Common {
    companion object {
        @IntDef(STATE_UNDEF, STATE_LOADING, STATE_LOADED, STATE_ERROR)
        @Retention(AnnotationRetention.SOURCE)
        annotation class States

        const val STATE_UNDEF = 0
        const val STATE_LOADING = 1
        const val STATE_LOADED = 2
        const val STATE_ERROR = 3

    }
}

