package com.example.reviewbookapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.BookInfo
import com.example.reviewbookapp.model.Item
import com.example.reviewbookapp.network.BooksAPI
import javax.inject.Inject

class BooksRepository @Inject constructor(private val booksAPI: BooksAPI) {
    suspend fun getAllBooks(bookName : String) : FirebaseDataQueryWrapper<BookInfo, Boolean, Exception>{
        val data = FirebaseDataQueryWrapper<BookInfo, Boolean, Exception>()
        data.loading = true
        try{

            data.data = booksAPI.getAllBooks(bookName)
            data.loading = false
        }catch(e : Exception){
            data.e = e
        }
        return data

    }
    suspend fun getSpecificBook(bookId : String) : FirebaseDataQueryWrapper<Item, Boolean, Exception>{
        val data = FirebaseDataQueryWrapper<Item, Boolean, Exception>()
        data.loading = true
        try{

            data.data = booksAPI.getSpesificBook(bookId)
            data.loading = false
        }catch(e : Exception){
            data.e = e
            Log.d(TAG, "getSpecificBook: ${e.message}")
        }
        return data
    }
}