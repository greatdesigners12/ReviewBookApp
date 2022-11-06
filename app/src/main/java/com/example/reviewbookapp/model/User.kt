package com.example.reviewbookapp.model

data class User(var display_name : String) {
    fun toMap() : MutableMap<String, Any>{
        return mutableMapOf(
            "display_name" to this.display_name
        )
    }
}