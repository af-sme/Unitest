package de.appsfactory.unitest.domain.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class EventTest {

    //this test basically checks, if there are no exceptions for the corner cases
    @Test
    fun `if two lists are empty, result is empty`() {
        assertTrue(Event.orderEvents(emptyList(), emptyList()).isEmpty())
    }

    private val today = LocalDate.of(2020, 6, 27)

    private val firstMatchPast = Match("", "", LocalDateTime.of(2020, 6, 21, 13, 30))
    private val firstMeetingPast = Meeting("", "", LocalDateTime.of(2020, 6, 21, 13, 30))
    private val secondMeetingPast = Meeting("", "", LocalDateTime.of(2020, 6, 22, 13, 30))
    private val thirdMeetingPast = Meeting("", "", LocalDateTime.of(2020, 6, 22, 13, 30))
    private val firstMatchToday = Match("", "", LocalDateTime.of(2020, 6, 27, 13, 30))
    private val firstMeetingToday = Meeting("", "", LocalDateTime.of(2020, 6, 27, 13, 30))
    private val secondsMeetingToday = Meeting("", "", LocalDateTime.of(2020, 6, 27, 15, 0))
    private val firstMatchFuture = Match("", "", LocalDateTime.of(2020, 6, 29, 13, 30))
    private val firstMeetingFuture = Meeting("", "", LocalDateTime.of(2020, 6, 29, 13, 30))
    private val secondMeetingFuture = Meeting("", "", LocalDateTime.of(2020, 6, 29, 18, 30))
    private val thirdMeetingFuture = Meeting("", "", LocalDateTime.of(2020, 6, 30, 13, 30))

    @Test
    fun `if matches list is empty, check appointments where in time`() {
        val meetings = listOf(
            secondMeetingFuture,
            firstMeetingToday,
            secondMeetingPast,
            thirdMeetingPast,
            firstMeetingFuture,
            secondsMeetingToday,
            firstMeetingPast,
            thirdMeetingFuture
        )
        val result = Event.orderEvents(emptyList(), meetings, today)

        assertEquals(Event.WhereInTime.PAST, result[0].whereInTime)
        assertEquals(Event.WhereInTime.PAST, result[1].whereInTime)
        assertEquals(Event.WhereInTime.PAST, result[2].whereInTime)
        assertEquals(Event.WhereInTime.TODAY, result[3].whereInTime)
        assertEquals(Event.WhereInTime.TODAY, result[4].whereInTime)
        assertEquals(Event.WhereInTime.FUTURE, result[5].whereInTime)
        assertEquals(Event.WhereInTime.FUTURE, result[6].whereInTime)
        assertEquals(Event.WhereInTime.FUTURE, result[7].whereInTime)
    }

    @Test
    fun `if meetings list is empty, check games where if time`() {
        val matches = listOf(
            firstMatchFuture,
            firstMatchPast,
            firstMatchToday
        )
        val result = Event.orderEvents(matches, emptyList(), today)

        assertEquals(Event.WhereInTime.PAST, result[0].whereInTime)
        assertEquals(Event.WhereInTime.TODAY, result[1].whereInTime)
        assertEquals(Event.WhereInTime.FUTURE, result[2].whereInTime)
    }

    @Test
    fun `if both lists are filled, check events where in time`() {
        val matches = listOf(
            firstMatchFuture,
            firstMatchPast,
            firstMatchToday
        )

        val meetings = listOf(
            secondMeetingFuture,
            firstMeetingToday,
            secondMeetingPast,
            thirdMeetingPast,
            firstMeetingFuture,
            secondsMeetingToday,
            firstMeetingPast,
            thirdMeetingFuture
        )

        val result = Event.orderEvents(matches, meetings, today)

        assertEquals("Check Event [0]", Event.WhereInTime.PAST, result[0].whereInTime)
        assertEquals("Check Event [1]", Event.WhereInTime.PAST, result[1].whereInTime)
        assertEquals("Check Event [2]", Event.WhereInTime.PAST, result[2].whereInTime)
        assertEquals("Check Event [3]", Event.WhereInTime.PAST, result[3].whereInTime)
        assertEquals("Check Event [4]", Event.WhereInTime.TODAY, result[4].whereInTime)
        assertEquals("Check Event [5]", Event.WhereInTime.TODAY, result[5].whereInTime)
        assertEquals("Check Event [6]", Event.WhereInTime.TODAY, result[6].whereInTime)
        assertEquals("Check Event [7]", Event.WhereInTime.FUTURE, result[7].whereInTime)
        assertEquals("Check Event [8]", Event.WhereInTime.FUTURE, result[8].whereInTime)
        assertEquals("Check Event [9]", Event.WhereInTime.FUTURE, result[9].whereInTime)
        assertEquals("Check Event [10]", Event.WhereInTime.FUTURE, result[10].whereInTime)
    }

    @Test
    fun `verify matches before meetings`() {
        val matches = listOf(
            firstMatchFuture,
            firstMatchPast,
            firstMatchToday
        )

        val meetings = listOf(
            secondMeetingFuture,
            firstMeetingToday,
            secondMeetingPast,
            thirdMeetingPast,
            firstMeetingFuture,
            secondsMeetingToday,
            firstMeetingPast,
            thirdMeetingFuture
        )

        val result = Event.orderEvents(matches, meetings, today)

        assertTrue("Event 0 should be a game", result[0] is Event.Game)
        assertTrue("Event 4 should be a game", result[4] is Event.Game)
        assertTrue("Event 7 should be a game", result[7] is Event.Game)
    }
}