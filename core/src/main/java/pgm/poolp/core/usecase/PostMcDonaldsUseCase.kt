package pgm.poolp.core.usecase

import pgm.poolp.core.data.PlacesRepository
import pgm.poolp.core.domain.McDonalds

class PostMcDonaldsUseCase(private val mPlacesRepository: PlacesRepository) {
    suspend fun invoke(): List<McDonalds> = mPlacesRepository.postMcDonalds()
}