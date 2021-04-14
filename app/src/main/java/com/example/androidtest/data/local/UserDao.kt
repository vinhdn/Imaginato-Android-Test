package com.example.androidtest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidtest.data.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getCurrentUser(): LiveData<User?>

    @Query("DELETE FROM users")
    suspend fun clear()
}