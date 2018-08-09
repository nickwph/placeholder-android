package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.Post

fun parse(post: com.nicholasworkshop.placeholder.api.Post): Post {
    return Post(
            id = post.id!!,
            userId = post.userId,
            title = post.title,
            body = post.body)
}

fun parse(postList: List<com.nicholasworkshop.placeholder.api.Post>): List<Post> {
    return postList.map(::parse)
}