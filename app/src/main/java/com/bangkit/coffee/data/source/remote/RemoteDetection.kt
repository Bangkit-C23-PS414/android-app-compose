package com.bangkit.coffee.data.source.remote

import com.squareup.moshi.Json
import java.util.Date

data class RemoteDetection(
    @Json(name = "id") val id: String,
    @Json(name = "user_id") val userId: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "result") val result: String?,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "detected_at") val detectedAt: Date,
)