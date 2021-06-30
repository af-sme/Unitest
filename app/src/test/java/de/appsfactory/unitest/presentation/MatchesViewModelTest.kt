package de.appsfactory.unitest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.interactor.GetMatchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MatchesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var useCase: GetMatchesUseCase

    private lateinit var viewModel: MatchesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MatchesViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when on refresh called, get matches use case executed`() = runBlocking {
        viewModel.onRefresh()
        verify(useCase).execute()
        Unit
    }

    @Test
    fun `when use case returns matches, then matches are shown`() = runBlocking {
        val matches = listOf(
            Match("1", "Match 1", LocalDateTime.of(2020, 1, 29, 11, 0)),
            Match("2", "Match 2", LocalDateTime.of(2020, 1, 30, 16, 30, 45))
        )
        val success = Result.success(matches)
        whenever(useCase.execute()).thenReturn(success)

        val result = mutableListOf<Match>()
        viewModel.matches.observeForever { result.addAll(it) }

        viewModel.onRefresh()

        assertEquals(matches, result)
    }

    @Test
    fun `when use case returns error, then error are shown`() = runBlocking {
        whenever(useCase.execute()).thenReturn(Result.failure(Exception("Oops!")))

        viewModel.onRefresh()

        assertEquals("Oops!", viewModel.error.value)
    }
}