package com.example.reviewbookapp.di

import com.example.reviewbookapp.network.BooksAPI
import com.example.reviewbookapp.repository.BooksRepository
import com.example.reviewbookapp.repository.FirestoreRepository
import com.example.reviewbookapp.utils.Constant.BASE_URL
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookAPI() : BooksAPI{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(BooksAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(api : BooksAPI) : BooksRepository = BooksRepository(api)

    @Singleton
    @Provides
    fun provideFirestoreRepository() : FirestoreRepository = FirestoreRepository(queryBook = FirebaseFirestore.getInstance())

}