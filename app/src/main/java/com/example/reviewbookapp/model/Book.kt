package com.example.reviewbookapp.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Book(
                @Exclude
                var id : String? = null,
                var title : String? = null,
                var notes : String? = null,
                var rating : Double? = 0.0,
                var author : String? = null,
                @get:PropertyName("image_url")
                @set:PropertyName("image_url")
                var imageUrl : String? = null,
                var userId : String? = null,
                var description : String? = null,
                var bookId : String? = null,
                var reading : Boolean? = null){
    fun toHashMap() : HashMap<String, Any?>{
        val data = HashMap<String, Any?>()
        if(this.id != null) data["id"] = this.id
        if(this.title != null) data["title"] = this.title
        if(this.notes != null) data["notes"] = this.notes
        if(this.rating != null) data["rating"] = this.rating
        if(this.imageUrl != null) data["image_url"] = this.imageUrl
        if(this.author != null) data["author"] = this.author
        if(this.userId != null) data["userId"] = this.userId
        if(this.description != null) data["description"] = this.description
        if(this.bookId != null) data["bookId"] = this.bookId
        if(this.reading != null) data["reading"] = this.reading
        return data


    }
}
