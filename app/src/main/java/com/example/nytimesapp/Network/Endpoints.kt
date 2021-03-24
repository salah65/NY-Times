package com.example.nytimesapp.Network

import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("/viewed/{period}.json")
    fun getMostPopularArticles(
        @Path("period") period: Int
    )
}