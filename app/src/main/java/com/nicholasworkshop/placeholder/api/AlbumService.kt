package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    fun getAlbumList(
            @Query("userId") userId: Long? = null
    ): Observable<List<Album>>

    @GET("/albums/{id}")
    fun getAlbum(
            @Path("id") id: String
    ): List<Album>

    @POST("/albums")
    fun createAlbum(
            @Body body: Album
    ): Observable<Album>
}

data class Album(
        val id: Long? = null,
        val userId: Long? = null,
        val title: String? = null)