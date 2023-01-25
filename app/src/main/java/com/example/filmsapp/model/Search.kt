package com.example.filmsapp.model

import java.io.Serializable

data class Search(
    val Response: String,
    val Search: List<Film>?,
    val totalResults: String?
): Serializable