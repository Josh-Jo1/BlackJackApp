package com.joshjo1.blackjackapp

import android.util.DisplayMetrics
import android.util.TypedValue

object Utils {

    /**
     * Convert dp to Int
     *
     * @param value in dp
     * @param metrics resource.displayMetrics (varies by activity)
     */
    fun dpToInt(value: Float, metrics: DisplayMetrics): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics).toInt()
    }
}