package com.example.nytimesapp.ui.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimesapp.Network.Resource
import com.example.nytimesapp.Network.RetrofitInstance
import com.example.nytimesapp.Network.handleError
import com.example.nytimesapp.mapper.toDomainModel
import com.example.nytimesapp.model.Article
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val articlesLiveData by lazy { MutableLiveData<Resource<List<Article>>>() }
    init {
        getArticles()
    }


    fun getArticles() {
        viewModelScope.launch(handleError(articlesLiveData)) {
            withContext(IO) {
                val response = RetrofitInstance.getService().getMostPopularArticles(1)
                if (response.isSuccessful)
                    articlesLiveData.postValue(Resource.success(response.body()?.results?.toDomainModel()))

            }
        }
    }
}