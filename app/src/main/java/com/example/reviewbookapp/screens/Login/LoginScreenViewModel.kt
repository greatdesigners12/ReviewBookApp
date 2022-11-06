package com.example.reviewbookapp.screens.Login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Companion.failed
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Companion.idle
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Companion.loadingStatus
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Companion.success
import com.example.reviewbookapp.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val _loading = MutableLiveData(idle)
    val loading : LiveData<FirebaseDataWrapper.Status> = _loading
    private val firebaseAuth = Firebase.auth
    private val _msg = MutableStateFlow(FirebaseDataWrapper("", FirebaseDataWrapper.Status.IDLE))
    val msg : StateFlow<FirebaseDataWrapper> = _msg


    fun signIn(email : String, password : String){

        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(loadingStatus)
            try{
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    _loading.postValue(success)
                    _msg.value = FirebaseDataWrapper("LOGIN", FirebaseDataWrapper.Status.SUCCESS)

                }.addOnFailureListener {

                    _loading.postValue(failed)
                    _msg.value = FirebaseDataWrapper(it.message.toString(), FirebaseDataWrapper.Status.FAILED)
                    Log.d(TAG, "signIn: ${it.message.toString()}")
                    Log.d(TAG, "signIn: ${msg.value}")
                }
            }catch(e : Exception){
                _loading.postValue(failed)
                _msg.tryEmit(FirebaseDataWrapper(e.message.toString(), FirebaseDataWrapper.Status.FAILED))
                Log.d(TAG, "signIn: ${e.message.toString()}")
            }
        }

    }

    fun register(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    it.user?.let { it1 -> addUserToFirestore(it1) }

                }.addOnFailureListener {
                    _msg.tryEmit(FirebaseDataWrapper(it.message.toString(), FirebaseDataWrapper.Status.FAILED))
                }

            }catch(e : Exception){
                _msg.tryEmit(FirebaseDataWrapper(e.message.toString(), FirebaseDataWrapper.Status.FAILED))
            }
        }
    }
    fun addUserToFirestore(user : FirebaseUser){
        val userInfo = User(display_name = user.email.toString()).toMap()
        FirebaseFirestore.getInstance().collection("userData").document(user.uid).set(userInfo).addOnSuccessListener {
            _msg.tryEmit(FirebaseDataWrapper("Your account has been created, please login", FirebaseDataWrapper.Status.SUCCESS))
        }.addOnFailureListener {
            _msg.tryEmit(FirebaseDataWrapper(it.message.toString(), FirebaseDataWrapper.Status.FAILED))
        }
    }
}