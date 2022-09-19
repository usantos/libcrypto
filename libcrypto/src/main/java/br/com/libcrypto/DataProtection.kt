package br.com.libcrypto

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class DataProtection {

    private val rsa = "RSA/ECB/PKCS1Padding"
    private val aes = "AES/CBC/PKCS5Padding"

    private var mSecretKeySpec: SecretKeySpec? = null
    private var mIvParameterSpec: IvParameterSpec? = null
    private var mGeneratedPassword: String = ""

    /*
    * Encrypt the data using AES Encryption
    * */
    @Throws(Exception::class)
    fun encryptAES(data: String): String {
        val cipher = Cipher.getInstance(aes)
        mGeneratedPassword = StringUtils().generateRandomValue()
        mSecretKeySpec = generateSecretKey(mGeneratedPassword)
        mIvParameterSpec = generateIvParameterSpec()
        cipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mIvParameterSpec)
        return Base64.encodeToString(cipher.doFinal(data.toByteArray()), Base64.NO_WRAP)
    }

    /*
     * Decrypt the data from AES Encryption
     * */
    fun decryptAES(data: String?): String {
        return try {
            if (data != null || data != "") {
                val cipher = Cipher.getInstance(aes)
                cipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec)

                clearValuesFromMemory()

                String(cipher.doFinal(Base64.decode(data, Base64.NO_WRAP)))
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    /*
    * Encrypt the data using RSA.
    * */
    @Throws(Exception::class)
    fun encryptRSA(data: String, publicCertificate: String): String {
        val pubKey: PublicKey? = getPublicKey(publicCertificate)
        val encryptCipher = Cipher.getInstance(rsa)
        encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey)
        val encryptedMessageBytes = encryptCipher.doFinal(
            data.toByteArray(
                StandardCharsets.UTF_8
            )
        )
        return Base64.encodeToString(encryptedMessageBytes, Base64.NO_WRAP)
    }

    private fun getPublicKey(base64PublicKey: String): PublicKey? {
        var base64PubKey = base64PublicKey
        val publicKey: PublicKey
        try {
            base64PubKey = if (base64PubKey.contains("-----BEGIN PUBLIC KEY-----")
                && base64PubKey.contains("-----END PUBLIC KEY-----")
            ) {
                base64PubKey
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "").trim { it <= ' ' }
            } else {
                throw InvalidKeyException("Invalid Public Key")
            }
            val keySpec = X509EncodedKeySpec(
                Base64.decode(
                    base64PubKey.toByteArray(), Base64.NO_WRAP
                )
            )
            val keyFactory = KeyFactory.getInstance("RSA")
            publicKey = keyFactory.generatePublic(keySpec)
            return publicKey
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun generateSecretKey(
        password: String
    ): SecretKeySpec {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(password.toCharArray(), ByteArray(8), 1035, 256)
        val secretKey = factory.generateSecret(spec)
        return SecretKeySpec(secretKey.encoded, "AES")
    }

    private fun generateIvParameterSpec(): IvParameterSpec {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }

    private fun clearValuesFromMemory() {
        mGeneratedPassword = ""
        mIvParameterSpec = null
        mSecretKeySpec = null
    }
}