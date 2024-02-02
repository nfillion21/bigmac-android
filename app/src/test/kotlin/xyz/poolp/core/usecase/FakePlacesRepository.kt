package xyz.poolp.core.usecase

import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.domain.McDonaldsPhoto

class FakePlacesRepository : PlacesRepository{
    override suspend fun postMcDonalds(latitude: Double, longitude: Double): List<McDonalds> {
        return listOf(
            McDonalds(
                identifier = "1",
                formattedAddress = "",
                shortFormattedAddress = "",
                latitude = 1.0,
                longitude = 1.0,
                locality = "",
                photosNames = emptyList()
            ),
            McDonalds(
                identifier = "2",
                formattedAddress = "",
                shortFormattedAddress = "",
                latitude = 1.0,
                longitude = 1.0,
                locality = "",
                photosNames = emptyList()
            ),
            McDonalds(
                identifier = "3",
                formattedAddress = "",
                shortFormattedAddress = "",
                latitude = 1.0,
                longitude = 1.0,
                locality = "",
                photosNames = emptyList()
            )
        )
    }

    override suspend fun getMcDonaldsPhoto(name: String): McDonaldsPhoto {
        return McDonaldsPhoto(
            url = "http://www.poolp.xyz/photo.png"
        )
    }
}