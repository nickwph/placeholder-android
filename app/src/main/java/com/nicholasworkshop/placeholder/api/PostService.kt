package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface PostService {

    @GET("/posts")
    fun getPostList(
            @Query("userId") userId: Long? = null
    ): Observable<List<Post>>

    @GET("/posts/{id}")
    fun getPost(
            @Path("id") id: String
    ): List<Post>

    @POST("/posts")
    fun createPost(
            @Body body: Post
    ): Observable<Post>
}

data class Post(
        val id: Long? = null,
        val userId: Long? = null,
        val title: String? = null,
        val body: String? = null)