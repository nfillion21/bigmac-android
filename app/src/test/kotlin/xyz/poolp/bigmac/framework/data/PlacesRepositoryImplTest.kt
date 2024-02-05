package xyz.poolp.bigmac.framework.data

import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPhotoRemote
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBody
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyCircle
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyLocation
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPostBodyLocationBias
import xyz.poolp.bigmac.framework.data.entity.McDonaldsRemote
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonaldsPhoto
import xyz.poolp.core.usecase.GetMcDonaldsPhotoUseCase

class PlacesRepositoryImplTest {

    private lateinit var placesRepository: PlacesRepository

    @Mock
    lateinit var ktorHttpClient: HttpClient

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        placesRepository = PlacesRepositoryImpl(ktorHttpClient)
    }

    class ApiClient(engine: HttpClientEngine) {
        private val httpClient = HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }

        suspend fun getMcDonaldsPhoto(): McDonaldsPhotoRemote = httpClient.get("url").body()
    }
    @Test
    fun `Get McDonald's photo, correct photo return`(): Unit = runBlocking {

        val mockEngine = MockEngine {request ->
            respond(
                content = ByteReadChannel("""{"name": "mcdonalds photo name",
                                                   "photoUri": "mcdonalds photo uri"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val apiClient = ApiClient(mockEngine)
        val mcDonaldsPhotoRemote = apiClient.getMcDonaldsPhoto()
        val str2 = mcDonaldsPhotoRemote.name
        val str2url = mcDonaldsPhotoRemote.photoUri

        //Assert.assertEquals("127.0.0.1", apiClient.getIp().ip)

        /*
        Mockito.`when`(
            ktorHttpClient.post("https://places.googleapis.com/v1/places:searchText") {
                setBody(
                    McDonaldsPostBody(
                        textQuery = "McDonald's",
                        maxResultCount = 20,
                        locationBias = McDonaldsPostBodyLocationBias(
                            circle = McDonaldsPostBodyCircle(
                                center = McDonaldsPostBodyLocation(
                                    latitude = 1.0,
                                    longitude = 1.0
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
            }.body<McDonaldsRemote>()
        ).thenReturn(
            McDonaldsRemote(
                places = emptyList()
            )
        )
        */
        //Truth.assertThat(photo.url == "http://www.poolp.xyz/photo.png").isTrue()
    }

    @Test
    fun postMcDonalds() {
    }

    @Test
    fun `getMcDonaldsPhoto`(): Unit = runBlocking {

        /*
        val mcDonaldsPhotoRemote: McDonaldsPhotoRemote =
            ktorHttpClient.get("https://places.googleapis.com/v1/$name/media") {
                url {
                    parameters.append("key", Env.PLACES_API_KEY)
                    parameters.append("skipHttpRedirect", "true")
                    parameters.append("maxHeightPx", "256")
                    //parameters.append("maxWidthPx", 300)
                }
            }

        return McDonaldsPhoto(
            url = mcDonaldsPhotoRemote.photoUri
        )
        */
        Mockito.`when`(
            ktorHttpClient.get("https://places.googleapis.com/v1/lamorlaye/media") {
                url {
                    parameters.append("key", Env.PLACES_API_KEY)
                    parameters.append("skipHttpRedirect", "true")
                    parameters.append("maxHeightPx", "256")
                    //parameters.append("maxWidthPx", 300)
                }
            }.body<McDonaldsPhotoRemote>()
        ).thenReturn(
            McDonaldsPhotoRemote(
                name = "lamorlaye",
                photoUri = "lamorlaye photo uri"
            )
        )

        val photo = placesRepository.getMcDonaldsPhoto("lamorlaye")
        Truth.assertThat(photo.url == "lamorlaye photo uri").isTrue()
    }
}