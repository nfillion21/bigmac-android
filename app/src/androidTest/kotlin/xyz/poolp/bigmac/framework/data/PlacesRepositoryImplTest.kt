package xyz.poolp.bigmac.framework.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.poolp.bigmac.framework.data.entity.McDonaldsPhotoRemote
import xyz.poolp.bigmac.framework.data.entity.McDonaldsRemote
import xyz.poolp.bigmac.framework.data.entity.mapToMcDonalds
import xyz.poolp.bigmac.framework.data.entity.mapToMcDonaldsPhoto
import xyz.poolp.bigmac.util.loadJSONFromAssets

@RunWith(AndroidJUnit4::class)
class PlacesRepositoryImplTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    class ApiClient(engine: HttpClientEngine) {
        private val httpClient = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        suspend fun getMcDonaldsPhoto(): McDonaldsPhotoRemote = httpClient.get("url").body()
        suspend fun postMcDonalds(): McDonaldsRemote = httpClient.post("url").body()
    }
    @Test
    fun getMcDonaldsPhoto(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{"name": "mcdonalds photo name",
                                                   "photoUri": "mcdonalds photo uri"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val apiClient = ApiClient(mockEngine)
        val mcDonaldsPhoto = apiClient.getMcDonaldsPhoto().mapToMcDonaldsPhoto()

        Truth.assertThat(mcDonaldsPhoto.url == "mcdonalds photo uri").isTrue()
    }

    @Test
    fun postMcDonalds(): Unit = runBlocking {
        val mockEngine = MockEngine { _ ->

            val data = context.loadJSONFromAssets("post_mcdonalds.json")

            respond(
                content = ByteReadChannel(data),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val apiClient = ApiClient(mockEngine)
        val mcDonaldsRemote = apiClient.postMcDonalds()

        val mcDonaldsList = mcDonaldsRemote.mapToMcDonalds()

        Truth.assertThat(mcDonaldsList[0].locality == "Lamorlaye").isTrue()
        Truth.assertThat(mcDonaldsList[1].photosNames[1].name == "places/ChIJbVJxWOcw5kcRIOTPM77OIx8/photos/AWU5eFhWF7WMcc14XRCzMPtz6L8fWOetzwP2xMJ6KKmjuf0-KG-Cogbm9M4q8ICTAUepMScu7Lw7rpQL1YBp1qTiUMH4gPbhXGiYMBoY1gzN1FwiTBckoq8pqeKxruAjcWGEMIdWedVVhNzUiEA84oBUvYiAh9wam0JfrzQ4").isTrue()
    }
}