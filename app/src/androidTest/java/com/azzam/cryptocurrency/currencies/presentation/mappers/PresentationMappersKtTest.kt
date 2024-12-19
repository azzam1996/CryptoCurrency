package com.azzam.cryptocurrency.currencies.presentation.mappers

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class PresentationMappersKtTest{

    @Test
    fun testToFormattedNumberWithNumberGreaterThanThousandWithoutDollarSign() = runBlocking {
        val number = 1234.89
        assertEquals("1.23K", number.toFormattedNumber(showDollarSign = false))
    }
    @Test
    fun testToFormattedNumberWithNumberGreaterThanThousandWithDollarSign() = runBlocking {
        val number = 1234.89
        assertEquals("\$1.23K", number.toFormattedNumber(showDollarSign = true))
    }

    @Test
    fun testToFormattedNumberWithNumberGreaterThanMillionWithoutDollarSign() = runBlocking {
        val number = 12345670.89
        assertEquals("12.35M", number.toFormattedNumber(showDollarSign = false))
    }
    @Test
    fun testToFormattedNumberWithNumberGreaterThanMillionWithDollarSign() = runBlocking {
        val number = 12345670.89
        assertEquals("\$12.35M", number.toFormattedNumber(showDollarSign = true))
    }

    @Test
    fun testToFormattedNumberWithNumberGreaterThanBillionWithoutDollarSign() = runBlocking {
        val number = 1234000000000.89
        assertEquals("1,234B", number.toFormattedNumber(showDollarSign = false))
    }
    @Test
    fun testToFormattedNumberWithNumberGreaterThanBillionWithDollarSign() = runBlocking {
        val number = 1234000000000.89
        assertEquals("\$1,234B", number.toFormattedNumber(showDollarSign = true))
    }
    @Test
    fun testToFormattedPercentageWithPositiveNumber() = runBlocking {
        val number = -4.2254499898963914
        assertEquals("-4.23%", number.toFormattedPercentage())
    }
    @Test
    fun testToFormattedPercentageWithNegativeNumber() = runBlocking {
        val number = -4.2244399898963914
        assertEquals("-4.22%", number.toFormattedPercentage())
    }
}