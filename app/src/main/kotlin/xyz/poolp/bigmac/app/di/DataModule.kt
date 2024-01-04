package xyz.poolp.bigmac.app.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import xyz.poolp.bigmac.framework.data.PlacesRepositoryImpl
import xyz.poolp.core.usecase.GetMcDonaldsPhotoUseCase
import xyz.poolp.core.usecase.PostMcDonaldsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = 15_000
                    socketTimeout = 15_000
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("http log: ", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("X-Android-Package", "xyz.poolp.bigmac")
                header("X-Android-Cert", "82CD83E15062CDECE43054523FDBDA4C80224E75")
            }
        }
    }

    @Provides
    @Singleton
    fun providePostMcDonaldsUseCase() =
        PostMcDonaldsUseCase(PlacesRepositoryImpl(provideKtorHttpClient()))

    @Provides
    @Singleton
    fun provideGetMcDonaldsPhotoUseCase() =
        GetMcDonaldsPhotoUseCase(PlacesRepositoryImpl(provideKtorHttpClient()))
}