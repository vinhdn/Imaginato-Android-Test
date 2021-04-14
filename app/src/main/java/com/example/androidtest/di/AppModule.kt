package com.example.androidtest.di

import android.content.Context
import com.example.androidtest.BuildConfig
import com.example.androidtest.data.local.AppDatabase
import com.example.androidtest.data.local.UserDao
import com.example.androidtest.data.remote.LoginRemoteDataSource
import com.example.androidtest.data.remote.LoginService
import com.example.androidtest.data.repository.LoginRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(loginService: LoginService) =
        LoginRemoteDataSource(loginService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideLoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: UserDao
    ) =
        LoginRepository(remoteDataSource, localDataSource)
}