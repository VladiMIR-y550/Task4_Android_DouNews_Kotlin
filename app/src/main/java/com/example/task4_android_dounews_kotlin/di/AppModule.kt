package com.example.task4_android_dounews_kotlin.di

import dagger.Module

@Module (includes = [ContextModule::class, RemoteModule::class, RepositoryModule::class, RoomModule::class, ViewModelFactoryModule::class])
class AppModule {
}