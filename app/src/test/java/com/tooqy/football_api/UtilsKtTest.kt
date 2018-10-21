package com.tooqy.football_api

import android.annotation.SuppressLint
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsKtTest {
    @Test
    fun testToSimpleString() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/28/2018")
        assertEquals("Wed, 28 Feb 2018", toSimpleString(date))
    }

    @SuppressLint("SimpleDateFormat")
    fun toSimpleString(date: Date?): String? = with(date ?: Date()) {
        SimpleDateFormat("EEE, dd MMM yyy").format(this)
    }
}