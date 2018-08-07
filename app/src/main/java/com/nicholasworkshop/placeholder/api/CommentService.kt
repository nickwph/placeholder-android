package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface CommentService {

    @GET("/comments")
    fun getCommentList(
            @Query("postId") postId: Long? = null
    ): Observable<List<Comment>>

    @GET("/comments/{id}")
    fun getComment(
            @Path("id") id: String
    ): List<Comment>

    @POST("/comments")
    fun createComment(
            @Body body: Comment
    ): Observable<Comment>
}

data class Comment(
        val id: Long? = null,
        val postId: Long? = null,
        val name: String? = null,
        val email: String? = null,
        val body: String? = null)