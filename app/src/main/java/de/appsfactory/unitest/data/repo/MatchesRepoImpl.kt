package de.appsfactory.unitest.data.repo

import de.appsfactory.unitest.data.dto.ApiMatch
import de.appsfactory.unitest.data.rest.MatchesService
import de.appsfactory.unitest.domain.entity.Match
import de.appsfactory.unitest.domain.repo.MatchesRepo
import java.time.LocalDateTime
import java.time.ZoneId

// Match id is not null and is not empty
//
// If match name is missed, name should be "Unknown"
//
// Match date is Mandatory
//
// Date in API is UTC, it's a string and looks like "2020-06-29T10:00:00"

class MatchesRepoImpl(private val matchesService: MatchesService): MatchesRepo {
    override suspend fun getMatches(): Result<List<Match>> {
        return try {
            Result.success(matchesService.getMatches().mapToDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun List<ApiMatch>.mapToDomain(): List<Match> {
    return mapNotNull {
        val id: String? = it.id
        val name: String = if (it.name.isNullOrBlank()) {
            "UNKNOWN"
        } else {
            it.name
        }
        val localDateTime: LocalDateTime? = it.utcTime?.let { utc ->
            try {
                LocalDateTime.parse(utc)
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())
                    .toLocalDateTime()
            } catch (e: Exception) {
                null
            }
        }
        if (id == null || localDateTime == null) {
            null
        } else {
            Match(id, name, localDateTime)
        }
    }
}
