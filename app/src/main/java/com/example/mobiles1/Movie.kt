package com.example.mobiles1

import java.util.UUID

data class Movie(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String
)
