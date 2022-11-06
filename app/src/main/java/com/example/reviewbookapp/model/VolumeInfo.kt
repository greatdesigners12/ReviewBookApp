package com.example.reviewbookapp.model


import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks,
    @SerializedName("infoLink")
    val infoLink: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("previewLink")
    val previewLink: String,
    @SerializedName("printType")
    val printType: String,
    @SerializedName("publishedDate")
    val publishedDate: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("title")
    val title: String
)