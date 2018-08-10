package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PostServiceIntegrationTest {

    lateinit var postService: PostService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        postService = module.postService(retrofit)
    }

    @Test
    fun getPostList() {
        val result = postService.getPostList().blockingFirst()
        assertThat(result.size).isEqualTo(100)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getPostList_byUserId() {
        val result = postService.getPostList(userId = 1).blockingFirst()
        assertThat(result.size).isEqualTo(10)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getPost() {
        val result = postService.getPost(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.title).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
        assertThat(result.userId).isEqualTo(1)
    }
}