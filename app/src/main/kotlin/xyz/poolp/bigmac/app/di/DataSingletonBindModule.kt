package xyz.poolp.bigmac.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.poolp.bigmac.framework.data.PlacesRepositoryImpl
import xyz.poolp.core.data.PlacesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSingletonBindModule {

    @Binds
    @Singleton
    abstract fun bindPlacesRepository(impl: PlacesRepositoryImpl): PlacesRepository
}