package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AlbumServiceIntegrationTest {

    lateinit var albumService: AlbumService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        albumService = module.albumService(retrofit)
    }

    @Test
    fun getAlbumList() {
        val result = albumService.getAlbumList().blockingFirst()
        assertThat(result.size).isEqualTo(100)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getAlbumList_byUserId() {
        val result = albumService.getAlbumList(userId = 1).blockingFirst()
        assertThat(result.size).isEqualTo(10)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getAlbum() {
        val result = albumService.getAlbum(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.title).isEqualTo("quidem molestiae enim")
        assertThat(result.userId).isEqualTo(1)
    }
}