package de.appsfactory.unitest

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class Playground {

    @Test
    fun `check rounding`() {
        println(10.5.roundToInt())
        println(11.5.roundToInt())
        println((-10.5).roundToInt())
        println((-11.5).roundToInt())
    }

    @Test
    fun checkDate() {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss"

        SimpleDateFormat(pattern)
            .apply { timeZone = TimeZone.getTimeZone("UTC") } //I have to do this
            //in production code to show local time,
            //and I want to verify, how does it work
            .parse("2020-02-05T06:17:54") //that's what I get from API
            .also { println(it) }

        SimpleDateFormat(pattern)
            .apply { timeZone = TimeZone.getTimeZone("UTC") }
            .parse("2020-10-05T06:17:54")
            .also { println(it) }
    }

    @Test
    fun checkDateThreeTen() {
        val requestedAtUtc = "2020-02-10T23:17:54"
        val local = LocalDateTime.parse(requestedAtUtc)
        println("local date time -> $local")
        val zonedUTC: ZonedDateTime = local.atZone(ZoneId.of("UTC"))
        println("zoned date time UTC -> $zonedUTC")
        val zonedCET: ZonedDateTime = local.atZone(ZoneId.systemDefault())
        println("zoned date time CET -> $zonedCET")
        val zonedHere = zonedUTC.withZoneSameInstant(ZoneId.systemDefault())
        println("zoned date time Leipzig -> $zonedHere")
    }
}