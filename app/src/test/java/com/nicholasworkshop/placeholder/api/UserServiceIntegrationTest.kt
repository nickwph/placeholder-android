package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserServiceIntegrationTest {

    lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        userService = module.userService(retrofit)
    }

    @Test
    fun getUserList() {
        val result = userService.getUserList().blockingFirst()
        assertThat(result.size).isEqualTo(10)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getUserList_byUserName() {
        val result = userService.getUserList(username = "Bret").blockingFirst()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getUser() {
        val result = userService.getUser(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.name).isEqualTo("Leanne Graham")
        assertThat(result.username).isEqualTo("Bret")
    }
}