package xyz.poolp.core.usecase

import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds

class PostMcDonaldsUseCase(private val mPlacesRepository: PlacesRepository) {
    suspend fun invoke(): List<McDonalds> = mPlacesRepository.postMcDonalds()
}