package xyz.poolp.core.usecase

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PostMcDonaldsUseCaseTest {

    private lateinit var postMcDonaldsUseCase: PostMcDonaldsUseCase
    private lateinit var fakePlacesRepository: FakePlacesRepository

    @Before
    fun setUp() {
        fakePlacesRepository = FakePlacesRepository()
        postMcDonaldsUseCase = PostMcDonaldsUseCase(fakePlacesRepository)
    }

    @Test
    fun `Get McDonald's List, correct mcdonald's list return`(): Unit = runBlocking {
        val mcdonalds = postMcDonaldsUseCase(
            latitude = 1.0,
            longitude = 1.0
        ).first()
        Truth.assertThat(mcdonalds.identifier == "1").isTrue()
    }
    @Test
    fun `Get McDonald's List, incorrect mcdonald's list return`(): Unit = runBlocking {
        val mcdonalds = postMcDonaldsUseCase(
            latitude = 1.0,
            longitude = 1.0
        ).last()
        Truth.assertThat(mcdonalds.identifier == "2").isFalse()
    }
}