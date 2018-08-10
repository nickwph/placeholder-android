package com.nicholasworkshop.placeholder.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PhotoServiceIntegrationTest {

    lateinit var photoService: PhotoService

    @BeforeEach
    fun setUp() {
        val module = ApiModule()
        val retrofit = module.retrofit()
        photoService = module.photoService(retrofit)
    }

    @Test
    fun getPhotoList() {
        val result = photoService.getPhotoList().blockingFirst()
        assertThat(result.size).isEqualTo(5000)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getPhotoList_byAlbumId() {
        val result = photoService.getPhotoList(albumId = 1).blockingFirst()
        assertThat(result.size).isEqualTo(50)
        assertThat(result[0].id).isEqualTo(1)
    }

    @Test
    fun getPhoto() {
        val result = photoService.getPhoto(1).blockingFirst()
        assertThat(result.id).isEqualTo(1)
        assertThat(result.title).isEqualTo("accusamus beatae ad facilis cum similique qui sunt")
        assertThat(result.albumId).isEqualTo(1)
    }
}