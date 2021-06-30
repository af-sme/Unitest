package de.appsfactory.unitest.domain.repo

import de.appsfactory.unitest.domain.entity.Match

interface MatchesRepo {
    suspend fun getMatches(): Result<List<Match>>
}