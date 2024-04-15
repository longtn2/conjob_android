package com.intern.conjob.arch.util

import java.util.regex.Pattern

//^ bắt đầu nhập chuỗi
//[A-Za-z] chuỗi chứa kí tự A-Z và a-z
//[#?!@$%^&*-]
//{8,32} chuỗi có 8 - 32 kí tự
fun String.isValidPassword(): Boolean {
    return Pattern.compile("^[A-Za-z0-9#?!@$%^&*-]{8,32}").matcher(this).matches()
}

//[A-Za-z] kí tự đầu tiên là chữ
// [A-Z0-9a-z. : kí tự chữ, số dấu có thể xuất hiện nhiều lần
//_+ dấu _ có thể có 1 hoặc nhiều
// - nối
// +@ : @ xuất hiện 1 lần
//[A-Za-z0-9.-]kí tự chữ, số dấu có thể xuất hiện nhiều lần
// + : nối
// \\ : biến kí tự đặc biệt thành kí tự thường
// .[A-Za-z]: có dấu chấm và chữ cái
// {2,64}: email có từ 2 đến 64 kí tự
fun String.isValidEmail(): Boolean {
    return Pattern.compile("[A-Za-z][A-Z0-9a-z._+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}").matcher(this).matches()
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