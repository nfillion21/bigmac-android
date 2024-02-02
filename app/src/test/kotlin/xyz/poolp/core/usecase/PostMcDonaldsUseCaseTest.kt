package xyz.poolp.core.usecase

import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import xyz.poolp.bigmac.framework.data.PlacesRepositoryImpl
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonalds

class PostMcDonaldsUseCaseTest {

    private lateinit var postMcDonaldsUseCase: PostMcDonaldsUseCase

    @Mock
    lateinit var placesRepository: PlacesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        postMcDonaldsUseCase = PostMcDonaldsUseCase(placesRepository)
    }

    @Test
    fun `Get McDonald's List, correct mcdonald's list return`(): Unit = runBlocking {
        Mockito.`when`(placesRepository.postMcDonalds(
            latitude = 1.0,
            longitude = 1.0
        ))
            .thenReturn(
                listOf(
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
            )

        val mcdonalds = postMcDonaldsUseCase(
            latitude = 1.0,
            longitude = 1.0
        ).first()
        Truth.assertThat(mcdonalds.identifier == "1").isTrue()
    }
    @Test
    fun `Get McDonald's List, incorrect mcdonald's list return`(): Unit = runBlocking {
        Mockito.`when`(placesRepository.postMcDonalds(
            latitude = 1.0,
            longitude = 1.0
        ))
            .thenReturn(
                listOf(
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
            )
        val mcdonalds = postMcDonaldsUseCase(
            latitude = 1.0,
            longitude = 1.0
        ).last()
        Truth.assertThat(mcdonalds.identifier == "2").isFalse()
    }
}