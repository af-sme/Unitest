package de.appsfactory.unitest.data.rest

import de.appsfactory.unitest.data.dto.ApiMatch

interface MatchesService {
    suspend fun getMatches(): List<ApiMatch>
}