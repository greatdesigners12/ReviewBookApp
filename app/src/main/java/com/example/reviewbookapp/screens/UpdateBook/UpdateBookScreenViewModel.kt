package com.example.reviewbookapp.screens.UpdateBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.model.Item
import com.example.reviewbookapp.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateBookScreenViewModel @Inject constructor(private val repository : FirestoreRepository) : ViewModel() {
    val data = MutableStateFlow(FirebaseDataQueryWrapper<Book, Boolean, Exception>(loading = true))
    fun getDetailBook(docId : String){
        viewModelScope.launch(Dispatchers.IO) {
            data.value.loading = true
            data.value = repository.getSpesificBooks(docId)

        }


    }

    fun updateBook(book : Book, navController: NavController){
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.updateBook(book, navController)
            if(result){
                navController.popBackStack()
            }

        }


    }
}