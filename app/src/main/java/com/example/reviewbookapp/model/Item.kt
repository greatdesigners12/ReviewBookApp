package com.example.reviewbookapp.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("searchInfo")
    val searchInfo: SearchInfo,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)