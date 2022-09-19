package br.com.libcrypto

import androidx.annotation.Keep

object Crypto{

    override fun toString(): String {
        return "Crypto Class"
    }

    /*
    * Create a instance of Crypto Class
    * */
    private val dataProtection = DataProtection()

    /*
    * Encrypt the data using AES Encryption
    * */
    fun encryptAES(data: String): String {
        return dataProtection.encryptAES(data)
    }

    /*
    * Decrypt the data from AES Encryption
    * */
    fun decryptAES(data: String?): String {
        return dataProtection.decryptAES(data)
    }

    /*
    * Encrypt the data using RSA.
    * */
    fun encryptRSA(data: String, publicCertificate: String): String {
        return dataProtection.encryptRSA(data, publicCertificate)
    }
}