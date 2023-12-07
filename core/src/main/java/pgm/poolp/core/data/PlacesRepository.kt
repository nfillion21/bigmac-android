package pgm.poolp.core.data

import pgm.poolp.core.domain.McDonalds

interface PlacesRepository {
    suspend fun postMcDonalds(): List<McDonalds>
}
