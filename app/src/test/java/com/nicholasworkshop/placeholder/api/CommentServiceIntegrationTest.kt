package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CommentServiceIntegrationTest {

    lateinit var commentService: CommentService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        commentService = module.commentService(retrofit)
    }

    @Test
    fun getCommentList() {
        val result = commentService.getCommentList().blockingFirst()
        assertThat(result.size).isEqualTo(500)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getCommentList_byPostId() {
        val result = commentService.getCommentList(postId = 1).blockingFirst()
        assertThat(result.size).isEqualTo(5)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getComment() {
        val result = commentService.getComment(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.name).isEqualTo("id labore ex et quam laborum")
        assertThat(result.postId).isEqualTo(1)
    }
}