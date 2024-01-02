package xyz.poolp.bigmac.framework.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPhotoRemote
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBody
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyCircle
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyLocation
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyLocationBias
import xyz.poolp.bigmac.framework.data.entity.McDonaldsRemote
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds
import xyz.poolp.core.domain.McDonaldsPhoto
import xyz.poolp.core.domain.McDonaldsPhotoName
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(private val ktorHttpClient: HttpClient) :
    PlacesRepository {
    override suspend fun postMcDonalds(latitude: Double, longitude: Double): List<McDonalds> {

        val mcDonaldsRemote: McDonaldsRemote =
            ktorHttpClient.post("https://places.googleapis.com/v1/places:searchText") {
                setBody(
                    McDonaldsPostBody(
                        textQuery = "McDonald's",
                        maxResultCount = 20,
                        locationBias = McDonaldsPostBodyLocationBias(
                            circle = McDonaldsPostBodyCircle(
                                center = McDonaldsPostBodyLocation(
                                    latitude = latitude,
                                    longitude = longitude
                                ),
                                radius = 500.0
                            ),
                        )
                    )
                )
                headers {
                    append("X-Goog-Api-Key", Env.PLACES_API_KEY)
                    append(
                        "X-Goog-FieldMask",
                        "places.id,places.formattedAddress,places.location,places.shortFormattedAddress,places.addressComponents,places.photos"
                    )
                }
            }.body()

        return mcDonaldsRemote.places.map { mcDonalds ->
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
    }

    override suspend fun getMcDonaldsPhoto(name: String): McDonaldsPhoto {
        val mcDonaldsPhotoRemote: McDonaldsPhotoRemote =
            ktorHttpClient.get("https://places.googleapis.com/v1/$name/media") {
                url {
                    parameters.append("key", Env.PLACES_API_KEY)
                    parameters.append("skipHttpRedirect", "true")
                    parameters.append("maxHeightPx", "256")
                    //parameters.append("maxWidthPx", 300)
                }
            }.body()

        return McDonaldsPhoto(
            url = mcDonaldsPhotoRemote.photoUri
        )
    }
}