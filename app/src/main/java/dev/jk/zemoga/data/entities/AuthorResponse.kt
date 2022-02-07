package dev.jk.zemoga.data.entities

data class AuthorResponse(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val website: String
)