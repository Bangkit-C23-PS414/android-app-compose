package com.bangkit.coffee.data.source.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "image_detections",
    indices = [
        Index("createdAt", orders = [Index.Order.DESC]),
        Index("label"),
        Index("syncAt", orders = [Index.Order.ASC]),
    ]
)
data class LocalImageDetection(
    @PrimaryKey
    val id: String,
    val fileURL: String,
    val blurHash: String,
    val isDetected: Boolean,
    val label: String,
    val confidence: Float,
    val inferenceTime: Int,
    val createdAt: LocalDateTime,
    val detectedAt: LocalDateTime,
    val syncAt: LocalDateTime,
)