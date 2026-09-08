package com.iscoding.common.util


import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.toEgpCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("ar", "EG"))
    format.currency = Currency.getInstance("EGP")
    return format.format(this)
}

fun String.maskCardNumber(): String {
    return if (length >= 4) "**** **** **** ${takeLast(4)}" else this
}

fun String.maskPhoneNumber(): String {
    return if (length >= 4) "${take(3)}****${takeLast(2)}" else this
}