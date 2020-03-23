package com.example.ign.api

import com.google.gson.annotations.SerializedName

data class IGNArticles (
    @SerializedName("contentId")
    val contentID : String,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("thumbnails")
    val thumbnails : List<ThumbNails>,
    @SerializedName("metadata")
    val metadata: MetaDataArticles,
    @SerializedName("tags")
    val tags : List<String>,
    @SerializedName("authors")
    val authors: List<Authors>
)

data class ThumbNails (
    @SerializedName("url")
    val url : String,
    @SerializedName("size")
    val size : String,
    @SerializedName("width")
    val width : Int,
    @SerializedName("height")
    val height: Int
)

data class MetaDataArticles (
    @SerializedName("headline")
    val headline : String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishDate")
    val publishDate: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("networks")
    val networks: List<String>,
    @SerializedName("state")
    val state: String,
    @SerializedName("objectName")
    val objectName: String
)

data class Authors (
    @SerializedName("name")
    val authorName: String,
    @SerializedName("thumbnail")
    val authorThumbNail: String
)

