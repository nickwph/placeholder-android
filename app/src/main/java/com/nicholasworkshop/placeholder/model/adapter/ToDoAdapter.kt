package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.ToDo

fun parse(toDo: com.nicholasworkshop.placeholder.api.ToDoService.ToDo): ToDo {
    return ToDo(
            id = toDo.id!!,
            userId = toDo.userId,
            title = toDo.title,
            completed = toDo.completed)
}

fun parse(toDoList: List<com.nicholasworkshop.placeholder.api.ToDoService.ToDo>): List<ToDo> {
    return toDoList.map(::parse)
}