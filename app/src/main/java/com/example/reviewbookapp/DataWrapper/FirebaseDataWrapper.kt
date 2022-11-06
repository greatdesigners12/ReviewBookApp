package com.example.reviewbookapp.DataWrapper

data class FirebaseDataWrapper(val message : String, val status : Status) {
    companion object{
        val idle = Status.IDLE
        val loadingStatus = Status.LOADING
        val failed = Status.FAILED
        val success = Status.SUCCESS
    }
    enum class Status{
        IDLE,
        LOADING,
        FAILED,
        SUCCESS
    }
}