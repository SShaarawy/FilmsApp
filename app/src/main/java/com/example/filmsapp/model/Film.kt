package com.example.filmsapp.model

import java.io.Serializable

data class Film(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
) : Serializable