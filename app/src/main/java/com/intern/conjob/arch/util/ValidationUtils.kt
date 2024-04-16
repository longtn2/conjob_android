package com.intern.conjob.arch.util

import java.util.regex.Pattern

//bao gồm từ 8 - 32 kí tự, gồm ít nhất một kí tự thường, kí tự in hoa, kí tự đặc biệt, số
fun String.isValidPassword(): Boolean {
    return Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,32}\$").matcher(this).matches()
}

// ít nhất 2 kí tự
// @
// ít nhất 1 kí tự
// .
// từ 2 đến 4 kí tự
// vd: ab@gmail.com
fun String.isValidEmail(): Boolean {
    return Pattern.compile("^[A-Za-z][\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matcher(this).matches()
}

//bao gồm kí tự A-Z, a-z và kí tự có dấu
//Kí tự đầu tiên phải viết Hoa
fun String.isValidName(): Boolean {
    return Pattern.compile("^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*\$").matcher(this).matches()
}

//bắt đầu bằng số 84 hoặc 0
//số tiếp theo là [3, 5, 7, 8, 9]
//phải đủ 10 số
fun String.isValidPhone(): Boolean {
    return Pattern.compile("(84|0)[3|5|7|8|9]([0-9]{8})").matcher(this).matches()
}