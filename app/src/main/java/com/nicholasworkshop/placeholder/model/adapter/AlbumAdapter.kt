package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.Album

fun parse(album: com.nicholasworkshop.placeholder.api.Album): Album {
    return Album(
            id = album.id!!,
            title = album.title,
            userId = album.userId)
}

fun parse(albumList: List<com.nicholasworkshop.placeholder.api.Album>): List<Album> {
    return albumList.map { parse(it) }
}