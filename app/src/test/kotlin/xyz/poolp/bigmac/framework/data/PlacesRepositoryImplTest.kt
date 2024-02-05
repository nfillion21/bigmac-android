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
import xyz.poolp.bigmac.framework.data.entity.mapToMcDonaldsPhoto
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonaldsPhoto
import xyz.poolp.core.usecase.GetMcDonaldsPhotoUseCase

class PlacesRepositoryImplTest {

    class ApiClient(engine: HttpClientEngine) {
        private val httpClient = HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }

        suspend fun getMcDonaldsPhoto(): McDonaldsPhotoRemote = httpClient.get("url").body()
    }
    @Test
    fun `Get McDonald's remote photo, correct photo return`(): Unit = runBlocking {
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
    fun postMcDonalds() {
    }
}