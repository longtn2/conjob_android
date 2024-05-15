package com.intern.conjob.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.intern.conjob.data.model.User

@Database(version = 1, entities = [User::class], exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "AppDataBase"
                ).build()
            }

            return instance!!
        }
    }

}
