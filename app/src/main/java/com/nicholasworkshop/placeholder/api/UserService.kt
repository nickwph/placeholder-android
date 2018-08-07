package com.nicholasworkshop.placeholder.api

import io.reactivex.Observable
import retrofit2.http.*

interface UserService {

    @GET("/users")
    fun getUserList(
            @Query("username") userId: Long? = null
    ): Observable<List<User>>

    @GET("/users/{id}")
    fun getUser(
            @Path("id") id: String
    ): List<User>

    @POST("/users")
    fun createUser(
            @Body body: User
    ): Observable<User>
}

data class User(
        val id: Long? = null,
        val name: String? = null,
        val username: String? = null,
        val email: String? = null,
        val address: Address? = null,
        val phone: String? = null,
        val website: String? = null,
        val company: Company? = null) {

    data class Company(
            val name: String? = null,
            val catchPhrase: String? = null,
            val bs: String? = null)

    data class Address(
            val street: String? = null,
            val suite: String? = null,
            val city: String? = null,
            val zipcode: String? = null,
            val geo: Geo? = null) {

        data class Geo(
                val lat: String? = null,
                val lng: String? = null)
    }
}