package com.intern.conjob.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val dob: String? = null,
    val address: String? = null,
    val avatar: String? = null,
)
