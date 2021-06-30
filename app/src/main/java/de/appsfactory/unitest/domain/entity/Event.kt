package de.appsfactory.unitest.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class Match(
    val id: String,
    val name: String,
    val localDateTime: LocalDateTime
)

data class Meeting(
    val id: String,
    val name: String,
    val localDateTime: LocalDateTime
)

sealed class Event(
    val localDateTime: LocalDateTime,
    val whereInTime: WhereInTime
) {

    class Game(val match: Match, whereInTime: WhereInTime) : Event(match.localDateTime, whereInTime)

    class Appointment(val meeting: Meeting, whereInTime: WhereInTime) :
        Event(meeting.localDateTime, whereInTime)

    enum class WhereInTime {
        PAST, TODAY, FUTURE
    }

    companion object {
        fun orderEvents(
            matches: List<Match>, meetings: List<Meeting>,
            today: LocalDate = LocalDate.now()
        ): List<Event> {
            return matches.map<Match, Event> { match ->
                Game(match, whereInTime(match.localDateTime, today))
            }.toMutableList()
                .apply {
                    addAll(
                        meetings.map<Meeting, Event> { meeting ->
                            Appointment(meeting, whereInTime(meeting.localDateTime, today))
                        })
                    sortBy { event -> event.localDateTime }
                }
        }

        private fun whereInTime(dateTime: LocalDateTime, today: LocalDate): WhereInTime {
            val date = dateTime.toLocalDate()
            return when {
                date.isBefore(today) -> {
                    WhereInTime.PAST
                }
                date == today -> {
                    WhereInTime.TODAY
                }
                else -> {
                    WhereInTime.FUTURE
                }
            }
        }
    }
}





//val games = matches.map<Match, Event> { match ->
//    Event.Game(match, Event.whereInTime(match.localDateTime, today))
//}
//val appointments = meetings.map<Meeting, Event> { meeting ->
//    Event.Appointment(meeting, Event.whereInTime(meeting.localDateTime, today))
//}
//
//return mutableListOf<Event>().apply {
//    addAll(games)
//    addAll(appointments)
//    sortBy { it.localDateTime }
//}