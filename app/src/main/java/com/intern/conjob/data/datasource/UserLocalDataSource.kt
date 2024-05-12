package com.intern.conjob.data.datasource

import com.intern.conjob.data.local.AppDataBase
import com.intern.conjob.data.local.UserDao
import com.intern.conjob.data.model.User

class UserLocalDataSource(
    private val userDao: UserDao
) {

    companion object {
        private var instance: UserLocalDataSource? = null

        fun getInstance(): UserLocalDataSource {
            if (instance == null) {
                AppDataBase.instance?.getUserDao()?.let {
                    val userDao: UserDao = it
                    instance = UserLocalDataSource(
                        userDao
                    )
                }
            }

            return instance!!
        }
    }

    suspend fun getUser(userId: Long): User {
        return userDao.getUser(userId)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }
}
