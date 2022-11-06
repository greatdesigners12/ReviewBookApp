package com.example.reviewbookapp.network

import com.example.reviewbookapp.model.BookInfo
import com.example.reviewbookapp.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksAPI {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") bookName :String) : BookInfo

    @GET("volumes/{BookId}")
    suspend fun getSpesificBook(@Path("BookId") bookId : String) : Item
}