package xyz.poolp.bigmac.app.framework.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBody
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyCircle
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyLocation
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyLocationBias
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsRemote
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(private val ktorHttpClient: HttpClient) :
    PlacesRepository {
    override suspend fun postMcDonalds(latitude:Double, longitude:Double): List<McDonalds> {

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
                                radius = 0.0
                            ),
                        )
                    )
                )
                headers {
                    append("X-Goog-Api-Key", Env.PLACES_API_KEY)
                    append(
                        "X-Goog-FieldMask",
                        "places.id,places.formattedAddress,places.location,places.shortFormattedAddress,places.addressComponents"
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

                McDonalds(
                    identifier = id,
                    formattedAddress = formattedAddress,
                    shortFormattedAddress = shortFormattedAddress,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    locality = city
                )
            }
        }

        /*
        val mcDonaldsRemote: McDonaldsRemote =
            ktorHttpClient.get("https://places.googleap.com/v1/places:searchtext").body()
        return listOf(
            McDonalds(
                identifier = "",
                address = "",
                location = Location(""),
                city = ""
            )
        )
        */
    }
}