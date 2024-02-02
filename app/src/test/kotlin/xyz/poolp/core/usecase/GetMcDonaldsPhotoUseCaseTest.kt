package xyz.poolp.core.usecase

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMcDonaldsPhotoUseCaseTest {

    private lateinit var getMcDonaldsPhotoUseCase: GetMcDonaldsPhotoUseCase
    private lateinit var fakePlacesRepository: FakePlacesRepository

    @Before
    fun setUp() {
        fakePlacesRepository = FakePlacesRepository()
        getMcDonaldsPhotoUseCase = GetMcDonaldsPhotoUseCase(fakePlacesRepository)
    }

    @Test
    fun `Get McDonald's photo, correct photo return`(): Unit = runBlocking {
        val photo = getMcDonaldsPhotoUseCase(name = "name")
        Truth.assertThat(photo.url == "http://www.poolp.xyz/photo.png").isTrue()
    }
}
