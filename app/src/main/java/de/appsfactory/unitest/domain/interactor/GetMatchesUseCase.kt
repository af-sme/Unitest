package de.appsfactory.unitest.domain.interactor

import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.repo.MatchesRepo

class GetMatchesUseCase(private val matchesRepo: MatchesRepo) {
    suspend fun execute(): Result<List<Match>> {
        return matchesRepo.getMatches()
    }
}
