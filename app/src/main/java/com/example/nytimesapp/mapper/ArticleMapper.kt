package com.example.nytimesapp.mapper

import com.example.nytimesapp.Network.response.Result
import com.example.nytimesapp.model.Article

fun List<Result>.toDomainModel(): List<Article> {
    return this.map {
        Article(
            title = it.title,
            date = it.published_date,
            byline = it.byline,
            url = it.url,
            source = it.source
        )
    }
}