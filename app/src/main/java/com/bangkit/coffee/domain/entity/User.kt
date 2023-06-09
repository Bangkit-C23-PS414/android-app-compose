package com.bangkit.coffee.domain.entity

data class User(
    val name: String,
    val email: String,
    val avatarUrl: String,
    val blurHash: String,
) {
    val cacheKey = "avatar-$blurHash"
}