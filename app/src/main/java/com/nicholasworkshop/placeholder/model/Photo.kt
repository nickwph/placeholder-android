package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Entity
data class Photo(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "albumId") val albumId: Long? = null,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "url") val url: String? = null,
        @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String? = null)

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    fun all(): LiveData<List<Photo>>

    @Query("SELECT * FROM photo WHERE id = :id LIMIT 1")
    fun findById(id: Long): Photo

    @Query("SELECT * FROM photo WHERE albumId = :albumId")
    fun findByAlbumId(albumId: Long): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photoList: List<Photo>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo): Long
}