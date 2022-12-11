package com.nextia.socioinfonavit.core.utils

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class CipherHelper(private var publicKey: String) {
    companion object {
        const val transformation = "RSA/ECB/PKCS1Padding"
        const val algorithm = "RSA"
    }

    fun encrypt(data: String): String {
        return try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())
            val bytes = cipher.doFinal(data.toByteArray())
            Base64.encodeToString(bytes, Base64.DEFAULT)
        }catch (_:Exception) {
            ""
        }

    }

    private fun getPublicKey() : PublicKey? {
        return try {
            val publicBytes: ByteArray = Base64.decode(publicKey, Base64.DEFAULT)
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory: KeyFactory = KeyFactory.getInstance(algorithm)
            keyFactory.generatePublic(keySpec)
        } catch (_:Exception) {
            null
        }

    }
}