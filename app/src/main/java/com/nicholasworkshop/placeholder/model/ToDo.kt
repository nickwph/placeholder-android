package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class ToDo(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "userId") val userId: Long? = null,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "completed") val completed: Boolean? = null)

@Dao
interface ToDoDao {

    @Query("SELECT * FROM toDo")
    fun all(): LiveData<List<ToDo>>

    @Query("SELECT * FROM toDo WHERE id = :id LIMIT 1")
    fun findById(id: Long): ToDo

    @Query("SELECT * FROM toDo WHERE userId = :userId")
    fun findByUserId(userId: Long): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(toDoList: List<ToDo>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDo: ToDo): Long
}