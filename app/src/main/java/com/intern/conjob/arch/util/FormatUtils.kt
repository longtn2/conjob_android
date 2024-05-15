package com.intern.conjob.arch.util

import com.intern.conjob.arch.util.Constants.NUMBER_BILLION_FORMAT
import com.intern.conjob.arch.util.Constants.NUMBER_MILLION_FORMAT
import com.intern.conjob.arch.util.Constants.NUMBER_THOUSAND_FORMAT
import com.intern.conjob.arch.util.Constants.ONE_BILLION
import com.intern.conjob.arch.util.Constants.ONE_MILLION
import com.intern.conjob.arch.util.Constants.ONE_THOUSAND

fun Long.format(): String {
    if (this >= ONE_BILLION) {
        return String.format(NUMBER_BILLION_FORMAT, this / ONE_BILLION)
    }

    if (this >= ONE_MILLION) {
        return String.format(NUMBER_MILLION_FORMAT, this / ONE_MILLION)
    }

    if (this >= ONE_THOUSAND) {
        return String.format(NUMBER_THOUSAND_FORMAT, this / ONE_THOUSAND)
    }
    return "$this"
}

fun Double.format(): String {
    if (this >= ONE_BILLION) {
        return String.format(NUMBER_BILLION_FORMAT, this / ONE_BILLION)
    }

    if (this >= ONE_MILLION) {
        return String.format(NUMBER_MILLION_FORMAT, this / ONE_MILLION)
    }

    if (this >= ONE_THOUSAND) {
        return String.format(NUMBER_THOUSAND_FORMAT, this / ONE_THOUSAND)
    }
    return "$this"
}
