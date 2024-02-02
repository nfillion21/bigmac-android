package xyz.poolp.core.usecase

import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.domain.McDonaldsPhoto

class GetMcDonaldsPhotoUseCase(private val mPlacesRepository: PlacesRepository) {
    suspend operator fun invoke(name: String): McDonaldsPhoto =
        mPlacesRepository.getMcDonaldsPhoto(name = name)
}