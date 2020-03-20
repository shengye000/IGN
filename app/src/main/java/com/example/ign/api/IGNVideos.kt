package com.example.ign.api

import com.google.gson.annotations.SerializedName

data class IGNVideos (
    @SerializedName("contentID")
    val contentID : String,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("thumbnails")
    val thumbnails : List<ThumbNails>,
    @SerializedName("metadata")
    val metadata: MetaDataVideos,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("assets")
    val authors: List<Assets>
)

data class MetaDataVideos (
    @SerializedName("title")
    val title : String,
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
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("videoSeries")
    val videoSeries: String
)

data class Assets(
    @SerializedName("url")
    val url: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)


