package com.nicholasworkshop.placeholder.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Entity
data class User(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "name") val name: String? = null,
        @ColumnInfo(name = "username") val username: String? = null,
        @ColumnInfo(name = "email") val email: String? = null,
        @ColumnInfo(name = "phone") val phone: String? = null,
        @ColumnInfo(name = "website") val website: String? = null,
        @ColumnInfo(name = "companyName") val companyName: String? = null,
        @ColumnInfo(name = "companyCatchPhrase") val companyCatchPhrase: String? = null,
        @ColumnInfo(name = "companyBs") val companyBs: String? = null,
        @ColumnInfo(name = "addressStreet") val addressStreet: String? = null,
        @ColumnInfo(name = "addressSuite") val addressSuite: String? = null,
        @ColumnInfo(name = "addressCity") val addressCity: String? = null,
        @ColumnInfo(name = "addressZipcode") val addressZipcode: String? = null,
        @ColumnInfo(name = "addressGeoLat") val addressGeoLat: String? = null,
        @ColumnInfo(name = "addressGeoLng") val addressGeoLng: String? = null)

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun all(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun findById(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userList: List<User>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Delete
    fun delete(friend: User)
}