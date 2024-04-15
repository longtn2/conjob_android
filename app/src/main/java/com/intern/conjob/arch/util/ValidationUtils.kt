package com.intern.conjob.arch.util

import java.util.regex.Pattern

//bao gồm từ 8 - 32 kí tự, gồm ít nhất một kí tự thường, kí tự in hoa, kí tự đặc biệt, số
fun String.isValidPassword(): Boolean {
    return Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,32}\$").matcher(this).matches()
}

fun String.isValidEmail(): Boolean {
    return Pattern.compile("^[A-Za-z][\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matcher(this).matches()
}

//^ bắt đầu nhập chuỗi
//[A-Za-z] chuỗi chứa kí tự A-Z và a-z
//{3,15} chuỗi có 3 - 15 kí tự
fun String.isValidName(): Boolean {
    return Pattern.compile("^[A-Za-z]{3,15}").matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    return Pattern.compile("(84|0)[3|5|7|8|9]([0-9]{8})").matcher(this).matches()
}