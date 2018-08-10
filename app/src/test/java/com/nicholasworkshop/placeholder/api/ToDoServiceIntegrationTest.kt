package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ToDoServiceIntegrationTest {

    lateinit var toDoService: ToDoService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        toDoService = module.toDoService(retrofit)
    }

    @Test
    fun getToDoList() {
        val result = toDoService.getToDoList().blockingFirst()
        assertThat(result.size).isEqualTo(200)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getToDoList_byUserId() {
        val result = toDoService.getToDoList(userId = 1).blockingFirst()
        assertThat(result.size).isEqualTo(20)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getToDo() {
        val result = toDoService.getToDo(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.title).isEqualTo("delectus aut autem")
        assertThat(result.userId).isEqualTo(1)
    }
}