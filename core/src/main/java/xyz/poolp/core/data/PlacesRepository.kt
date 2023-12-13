package xyz.poolp.core.data

import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.domain.McDonaldsPhoto

interface PlacesRepository {
    suspend fun postMcDonalds(latitude:Double, longitude:Double): List<McDonalds>
    suspend fun getMcDonaldsPhoto(name:String): McDonaldsPhoto
}
