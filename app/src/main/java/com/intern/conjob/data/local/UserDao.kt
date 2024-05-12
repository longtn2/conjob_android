package com.intern.conjob.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intern.conjob.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("Select * from User where id = :id limit 1")
    suspend fun getUser(id: Long): User

    @Query("Delete from User where id = :id")
    suspend fun deleteUser(id: Long)

}
