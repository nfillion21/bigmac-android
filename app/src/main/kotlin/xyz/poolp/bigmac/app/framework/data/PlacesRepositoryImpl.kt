package xyz.poolp.bigmac.app.framework.data

import android.location.Location
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import pgm.poolp.core.data.PlacesRepository
import pgm.poolp.core.domain.McDonalds
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBody
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyCircle
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyLocation
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsPostBodyLocationBias
import xyz.poolp.bigmac.app.framework.data.entity.McDonaldsRemote
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(private val ktorHttpClient: HttpClient) :
    PlacesRepository {
    override suspend fun postMcDonalds(): List<McDonalds> {

        val mcDonaldsRemote: McDonaldsRemote =
            ktorHttpClient.post("https://places.googleap.com/v1/places:searchtext") {
                setBody(
                    McDonaldsPostBody(
                        textQuery = "McDonald's",
                        maxResultCount = 11,
                        locationBias = McDonaldsPostBodyLocationBias(
                            circle = McDonaldsPostBodyCircle(
                                center = McDonaldsPostBodyLocation(
                                    latitude = 49.145964299999996,
                                    longitude = 2.4415903
                                ),
                                radius = 50000.0
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

        return listOf(
            McDonalds(
                identifier = "",
                address = "",
                location = Location(""),
                city = ""
            )
        )

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