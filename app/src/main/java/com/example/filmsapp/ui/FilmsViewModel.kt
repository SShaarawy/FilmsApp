package com.example.filmsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsapp.api.FilmsApi.retrofitService
import com.example.filmsapp.model.FilmDetails
import com.example.filmsapp.model.Search
import kotlinx.coroutines.launch

class FilmsViewModel : ViewModel() {

    private val _filmList = MutableLiveData<Search>()
    val filmList : LiveData<Search> = _filmList

    private val _filmDetails = MutableLiveData<FilmDetails>()
    val filmDetails : LiveData<FilmDetails> = _filmDetails

    fun searchFilms(query: String){
        viewModelScope.launch {
            val response = retrofitService.searchFilms(query)
            if(response.isSuccessful){
                _filmList.value = response.body()
            }
        }
    }

    fun getFilmDetails(query: String) {
        viewModelScope.launch {
            val response = retrofitService.getFilmDetails(query)
            if(response.isSuccessful){
                _filmDetails.value = response.body()
            }
        }
    }
}