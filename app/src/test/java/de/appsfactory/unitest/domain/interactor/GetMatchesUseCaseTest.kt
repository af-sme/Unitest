package de.appsfactory.unitest.domain.interactor

import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.repo.MatchesRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class GetMatchesUseCaseTest {

    @Test
    fun `verify usecase result`() = runBlocking {
        val success = Result.success(
            listOf(
                Match("1", "Match 1", LocalDateTime.of(2020, 1, 29, 11, 0)),
                Match("2", "Match 2", LocalDateTime.of(2020, 1, 30, 16, 30, 45))
            )
        )
        val repo = object : MatchesRepo {
            override suspend fun getMatches(): Result<List<Match>> {
                return success
            }
        }
        val useCase = GetMatchesUseCase(repo)
        assertEquals(success, useCase.execute())
    }
}