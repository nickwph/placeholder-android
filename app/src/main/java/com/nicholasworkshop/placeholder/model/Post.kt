package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class Post(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "userId") val userId: Long? = null,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "body") val body: String? = null)

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun all(): LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE id = :id LIMIT 1")
    fun findById(id: Long): LiveData<Post>

    @Query("SELECT * FROM post WHERE userId = :userId")
    fun findByUserId(userId: Long): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(postList: List<Post>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post): Long
}