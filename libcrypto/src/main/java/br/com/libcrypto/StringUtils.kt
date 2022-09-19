package br.com.libcrypto

import java.util.*

internal class StringUtils {

    fun generateRandomValue(): String {
        val allowed = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789!@#%&*()"
        var response = ""
        var pos: Int
        var end = 0
        while (end < 128) {
            ++end
            val rand = Random()
            pos = rand.nextInt(allowed.length)
            response += allowed[pos]
        }
        return response
    }
}