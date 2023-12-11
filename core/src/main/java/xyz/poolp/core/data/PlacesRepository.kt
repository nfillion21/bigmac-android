package xyz.poolp.core.data

import xyz.poolp.core.domain.McDonalds

interface PlacesRepository {
    suspend fun postMcDonalds(latitude:Double, longitude:Double): List<McDonalds>
}
