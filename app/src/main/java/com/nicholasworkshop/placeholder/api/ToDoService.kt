package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface ToDoService {

    @GET("/todos")
    fun getToDoList(
            @Query("userId") userId: Long? = null
    ): Observable<List<ToDo>>

    @GET("/todos/{id}")
    fun getToDo(
            @Path("id") id: String
    ): List<ToDo>

    @POST("/todos")
    fun createToDo(
            @Body body: ToDo
    ): Observable<ToDo>
}

data class ToDo(
        val id: Long? = null,
        val userId: Long? = null,
        val title: String? = null,
        val completed: Boolean? = null)