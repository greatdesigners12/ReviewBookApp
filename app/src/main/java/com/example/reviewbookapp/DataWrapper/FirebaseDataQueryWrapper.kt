package com.example.reviewbookapp.DataWrapper

import java.lang.Exception

data class FirebaseDataQueryWrapper<T, Boolean, Exception>(var data : T? = null, var loading : Boolean? = null, var e : Exception? = null)
