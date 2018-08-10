package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.Photo

fun parse(photo: com.nicholasworkshop.placeholder.api.PhotoService.Photo): Photo {
    return Photo(
            id = photo.id!!,
            albumId = photo.albumId,
            title = photo.title,
            url = photo.url,
            thumbnailUrl = photo.thumbnailUrl)
}

fun parse(photoList: List<com.nicholasworkshop.placeholder.api.PhotoService.Photo>): List<Photo> {
    return photoList.map(::parse)
}