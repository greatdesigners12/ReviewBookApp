package com.example.reviewbookapp.screens.BookList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.BookInfo
import com.example.reviewbookapp.repository.BooksRepository
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BookListViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel(){
    var bookList = MutableStateFlow(FirebaseDataQueryWrapper<BookInfo, Boolean, Exception>(loading = false))

    init{
        getAllBook("android")
    }

    fun getAllBook(bookName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                bookList.value.loading = true
                bookList.value = repository.getAllBooks(bookName)
            }catch (e : java.lang.Exception){
                Log.d(TAG, "getAllBook: ${e.message}")
            }
            

        }
    }
}