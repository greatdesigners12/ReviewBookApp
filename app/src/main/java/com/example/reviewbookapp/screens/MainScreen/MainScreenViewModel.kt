package com.example.reviewbookapp.screens.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: FirestoreRepository) : ViewModel() {
    val data = MutableStateFlow(FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>())
    val readingBookData = MutableStateFlow(FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>())

    fun getAllSavedBooks(){
        viewModelScope.launch(Dispatchers.IO) {
            data.value.loading = true
            data.value = repository.getAllBooks()
            if(data.value != null) data.value.loading = false
        }
    }

    fun getAllReadingBooks(){
        viewModelScope.launch(Dispatchers.IO) {
            readingBookData.value.loading = true
            readingBookData.value = repository.getReadingBook()
            if(readingBookData.value != null) readingBookData.value.loading = false
        }
    }
}