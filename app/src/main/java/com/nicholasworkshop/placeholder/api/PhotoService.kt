package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface PhotoService {

    @GET("/photos")
    fun getPhotoList(
            @Query("albumId") albumId: Long? = null
    ): Observable<List<Photo>>

    @GET("/photos/{id}")
    fun getPhoto(
            @Path("id") id: Long
    ): Observable<Photo>

    @POST("/photos")
    fun createPhoto(
            @Body body: Photo
    ): Observable<Photo>

    data class Photo(
            val id: Long? = null,
            val albumId: Long? = null,
            val title: String? = null,
            val url: String? = null,
            val thumbnailUrl: String? = null)
}