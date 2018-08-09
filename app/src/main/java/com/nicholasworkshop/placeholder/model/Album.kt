package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class Album(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "userId") val userId: Long? = null,
        @ColumnInfo(name = "title") val title: String? = null)

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun all(): LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE id = :id LIMIT 1")
    fun findById(id: Long): Album

    @Query("SELECT * FROM album WHERE userId = :userId")
    fun findByUserId(userId: Long): LiveData<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albumList: List<Album>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album): Long
}