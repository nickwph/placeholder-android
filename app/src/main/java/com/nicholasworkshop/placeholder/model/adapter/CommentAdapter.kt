package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.Comment

fun parse(comment: com.nicholasworkshop.placeholder.api.Comment): Comment {
    return Comment(
            id = comment.id!!,
            postId = comment.postId,
            name = comment.name,
            email = comment.email,
            body = comment.body)
}

fun parse(postList: List<com.nicholasworkshop.placeholder.api.Comment>): List<Comment> {
    return postList.map(::parse)
}