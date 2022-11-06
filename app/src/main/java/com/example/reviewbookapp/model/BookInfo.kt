package com.example.reviewbookapp.model


import com.google.gson.annotations.SerializedName

data class BookInfo(
    @SerializedName("items")
    val items: List<Item>
)