package xyz.poolp.bigmac.framework.data.entity

import kotlinx.serialization.Serializable
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.domain.McDonaldsPhoto
import xyz.poolp.core.domain.McDonaldsPhotoName

@Serializable
data class McDonaldsRemote(
    val places: List<McDonaldsRemotePlaces>,
)

@Serializable
data class McDonaldsRemotePlaces(
    val id: String,
    val formattedAddress: String,
    val location: McDonaldsRemoteLocation,
    val shortFormattedAddress: String,
    val addressComponents: List<McDonaldsRemoteAddressComponent>,
    val photos: List<McDonaldsRemotePhoto>
)

@Serializable
data class McDonaldsRemoteAddressComponent(
    val longText: String,
    val shortText: String,
    val types: List<String>,
    val languageCode: String
)

@Serializable
data class McDonaldsRemotePhoto(
    val name: String,
    val widthPx: Int,
    val heightPx: Int
)

@Serializable
data class McDonaldsRemoteLocation(
    val latitude: Double,
    val longitude: Double
)

fun McDonaldsRemote.mapToMcDonalds(): List<McDonalds> =
    places.map { mcDonalds ->
        with(mcDonalds) {

            var city = ""
            loop@ for (component in addressComponents) {
                for (type in component.types) {
                    if (type == "locality") {
                        city = component.longText
                        break@loop
                    }
                }
            }

            val photos = photos.map {
                McDonaldsPhotoName(
                    name = it.name,
                )
            }

            McDonalds(
                identifier = id,
                formattedAddress = formattedAddress,
                shortFormattedAddress = shortFormattedAddress,
                latitude = location.latitude,
                longitude = location.longitude,
                locality = city,
                photosNames = photos
            )
        }
}
