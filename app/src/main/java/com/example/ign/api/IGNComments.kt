package com.example.ign.api

import com.google.gson.annotations.SerializedName

data class IGNComments (
    @SerializedName("id")
    val id: String,
    @SerializedName("count")
    val count: Int
)