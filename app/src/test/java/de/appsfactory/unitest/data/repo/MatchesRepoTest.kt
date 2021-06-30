package de.appsfactory.unitest.data.repo


import de.appsfactory.unitest.data.dto.ApiMatch
import de.appsfactory.unitest.data.rest.MatchesService
import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.repo.MatchesRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.util.*

// Match id is not null and is not empty
//
// If match name is missed, name should be "Unknown"
//
// Match date is Mandatory
//
// Date in API is UTC, it's a string and looks like "2020-06-29T10:00:00"

@RunWith(MockitoJUnitRunner::class)
class MatchesRepoTest {

    private val matchWithNullId = ApiMatch(null, "Match", "2020-06-29T10:00:00")
    private val matchWithNullName = ApiMatch("1", null, "2020-06-29T10:00:00")
    private val matchWithBlankName = ApiMatch("1", "    ", "2020-06-29T10:00:00")
    private val matchWithNullDate = ApiMatch("1", "Match", null)
    private val matchWithBadDate = ApiMatch("1", "Match", "can't parse this")

    @Mock
    private lateinit var matchesService: MatchesService
    private lateinit var matchesRepo: MatchesRepo

    @Before
    fun setUp() {
        matchesRepo = MatchesRepoImpl(matchesService)
    }


//    @Test
//    fun `check matches mapping`() = runBlocking {
//        //test should not depend on unpredictable parameters
//        TimeZone.setDefault(TimeZone.getTimeZone("CET"))
//        whenever(matchesService.getMatches()).thenReturn(
//            listOf(
//                ApiMatch("1", "Match 1", "2020-01-29T10:00:00"),
//                ApiMatch("2", "Match 2", "2020-01-30T15:30:45")
//            )
//        )
//        val result = matchesRepo.getMatches()
//        assertEquals(
//            listOf(
//                Match("1", "Match 1", LocalDateTime.of(2020, 1, 29, 11, 0)),
//                Match("2", "Match 2", LocalDateTime.of(2020, 1, 30, 16, 30, 45))
//            ),
//            result.getOrThrow()
//        )
//    }

//    @Test
//    fun `if service throws exception then repo returns failure`() = runBlocking {
//        whenever(matchesService.getMatches()).thenAnswer { throw SocketTimeoutException("Oops!") }
//        val result = matchesRepo.getMatches()
//        assertTrue(result.isFailure)
//    }
//
//    @Test
//    fun `if service returns empty list then repo return success with empty list`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(emptyList())
//        val result = matchesRepo.getMatches()
//        assertTrue(result.isSuccess)
//        assertTrue(result.getOrThrow().isEmpty())
//    }
//
//    @Test
//    fun `matches with null id should be omitted`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(listOf(matchWithNullId))
//        val result = matchesRepo.getMatches()
//        assertTrue(result.getOrThrow().isEmpty())
//    }
//
//    @Test
//    fun `match without name should get Unknown name`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(listOf(matchWithNullName))
//        val result = matchesRepo.getMatches()
//        assertTrue(result.getOrThrow()[0].name == "Unknown")
//    }
//
//    @Test
//    fun `match with blank name should get Unknown name`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(listOf(matchWithBlankName))
//        val result = matchesRepo.getMatches()
//        assertTrue(result.getOrThrow()[0].name == "Unknown")
//    }
//
//    @Test
//    fun `match with null date should be omitted`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(listOf(matchWithNullDate))
//        val result = matchesRepo.getMatches()
//        assertTrue(result.getOrThrow().isEmpty())
//    }
//
//    @Test
//    fun `match with bad date should be omitted`() = runBlocking {
//        whenever(matchesService.getMatches()).thenReturn(listOf(matchWithBadDate))
//        val result = matchesRepo.getMatches()
//        assertTrue(result.getOrThrow().isEmpty())
//    }
//
//    @Test
//    fun `check matches mapping`() = runBlocking {
//        //test should not depend on unpredictable parameters
//        TimeZone.setDefault(TimeZone.getTimeZone("CET"))
//        whenever(matchesService.getMatches()).thenReturn(
//            listOf(
//                ApiMatch("1", "Match 1", "2020-01-29T10:00:00"),
//                ApiMatch("2", "Match 2", "2020-01-30T15:30:45")
//            )
//        )
//        val result = matchesRepo.getMatches()
//        assertEquals(
//            listOf(
//                Match("1", "Match 1", LocalDateTime.of(2020, 1, 29, 11, 0)),
//                Match("2", "Match 2", LocalDateTime.of(2020, 1, 30, 16, 30, 45))
//            ),
//            result.getOrNull()
//        )
//    }
}