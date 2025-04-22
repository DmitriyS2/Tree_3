package com.sdv.common

import java.math.BigInteger
import java.security.MessageDigest

fun encrypt(input: String): String {
    val md = MessageDigest.getInstance("SHA-1")
    val messageDigest = md.digest(input.toByteArray())
    val no = BigInteger(1, messageDigest)
    var hashText = no.toString(16)
    while (hashText.length < 20) {
        hashText = "0$hashText"
    }
    return hashText
}