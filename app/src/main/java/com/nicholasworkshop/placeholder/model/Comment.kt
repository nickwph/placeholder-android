package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class Comment(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "postId") val postId: Long? = null,
        @ColumnInfo(name = "name") val name: String? = null,
        @ColumnInfo(name = "email") val email: String? = null,
        @ColumnInfo(name = "body") val body: String? = null)

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun all(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment WHERE id = :id LIMIT 1")
    fun findById(id: Long): Comment

    @Query("SELECT * FROM comment WHERE postId = :postId")
    fun findByPostId(postId: Long): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(commentList: List<Comment>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment): Long
}