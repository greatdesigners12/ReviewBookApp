package com.example.reviewbookapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val queryBook : FirebaseFirestore) {

    suspend fun getAllBooks() : FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>{
        val dataOrException = FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        try{
            dataOrException.loading = true
            dataOrException.data = queryBook.collection("userBookList").whereEqualTo("userId", currentUserId).whereEqualTo("reading", false).get().await().documents.map{
                it.toObject(Book::class.java)!!
            }
             dataOrException.loading = false
        }catch (e : FirebaseFirestoreException){
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getReadingBook() : FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>{
        val dataOrException = FirebaseDataQueryWrapper<List<Book>, Boolean, Exception>()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        try{
            dataOrException.loading = true
            dataOrException.data = queryBook.collection("userBookList").whereEqualTo("userId", currentUserId).whereEqualTo("reading", true).get().await().documents.map{
                it.toObject(Book::class.java)!!
            }
            dataOrException.loading = false
            dataOrException.loading = false
        }catch (e : FirebaseFirestoreException){
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getSpesificBooks(docId : String) : FirebaseDataQueryWrapper<Book, Boolean, Exception> {
        val dataOrException = FirebaseDataQueryWrapper<Book, Boolean, Exception>()
        try{
            dataOrException.loading = true
            dataOrException.data =  queryBook.collection("userBookList").document(docId).get().await().toObject(Book::class.java)
            if(dataOrException.data != null) dataOrException.loading = false
        }catch (e : FirebaseFirestoreException){
            dataOrException.e = e

        }
        return dataOrException
    }

    suspend fun updateBook(book : Book, navController: NavController) : Boolean{
        var succeedd = false
        try{


            queryBook.collection("userBookList").document(book.id!!).update(book.toHashMap()).await()
            succeedd = true
        }catch (e : FirebaseFirestoreException){

        }
        return succeedd
    }




}