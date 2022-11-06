package com.example.reviewbookapp.screens.Details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.model.Item
import com.example.reviewbookapp.repository.BooksRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {
    val data = MutableStateFlow(FirebaseDataQueryWrapper<Item, Boolean, Exception>(loading = true))
    fun getDetailBook(bookId : String){
        viewModelScope.launch(Dispatchers.IO) {
            data.value.loading = true
            data.value = repository.getSpecificBook(bookId)

        }


    }

    fun saveToFirebase(curData : Book, navController: NavController, isSaved : Boolean){
        val instance = Firebase.firestore.collection("userBookList")
        if(isSaved){
            instance.whereEqualTo("bookId", curData.bookId).get().addOnSuccessListener {documents ->
                for(document in documents){
                    instance.document(document.id).delete()
                }
                navController.popBackStack()
            }
        }else{
            Log.d(TAG, "saveToFirebase: ${curData}")
            instance.add(curData).addOnSuccessListener {
                val docId = it.id
                instance.document(docId).update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnSuccessListener {
                        navController.popBackStack()
                    }
            }.addOnFailureListener {
                Log.d("error", it.message.toString())
            }
        }

    }

    fun checkIfSaved(bookId: String) : Boolean{
        val instance = Firebase.firestore
        var result = false
        instance.collection("userBookList").whereEqualTo("bookId", bookId).get().addOnSuccessListener {documents ->
            result = documents.size() > 0
        }
        return result
    }

    fun resetData(){
        data.value = FirebaseDataQueryWrapper(loading = true)
        Log.d(TAG, "getDetailBook: brooowada")
    }
}