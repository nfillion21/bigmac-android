package xyz.poolp.core.usecase

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import xyz.poolp.core.data.PlacesRepository
import xyz.poolp.core.domain.McDonaldsPhoto

class GetMcDonaldsPhotoUseCaseTest {

    private lateinit var getMcDonaldsPhotoUseCase: GetMcDonaldsPhotoUseCase

    @Mock
    lateinit var placesRepository: PlacesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getMcDonaldsPhotoUseCase = GetMcDonaldsPhotoUseCase(placesRepository)
    }

    @Test
    fun `Get McDonald's photo, correct photo return`(): Unit = runBlocking {
        Mockito.`when`(
            placesRepository.getMcDonaldsPhoto(
                name = "name"
            )
        ).thenReturn(
            McDonaldsPhoto(
                url = "http://www.poolp.xyz/photo.png"
            )
        )

        val photo = getMcDonaldsPhotoUseCase(name = "name")
        Truth.assertThat(photo.url == "http://www.poolp.xyz/photo.png").isTrue()
    }
}
