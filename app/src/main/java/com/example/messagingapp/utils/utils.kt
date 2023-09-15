package com.example.messagingapp.utils

import android.content.Context
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


lateinit var ivValue: ByteArray
lateinit var ivParams :ByteArray
fun encrypt(context: Context, strToEncrypt: String): ByteArray {
    val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
    val key = generateKey("password")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    val rnd = SecureRandom()
    val iv = ByteArray(cipher.blockSize)
    rnd.nextBytes(iv)
    val ivParams = IvParameterSpec(iv)
    cipher.init(Cipher.ENCRYPT_MODE, key,ivParams)
    return cipher.doFinal(plainText)
}
fun decrypt(context: Context, dataToDecrypt: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    val key = generateKey("password")
    val rnd = SecureRandom()
    val iv = ByteArray(cipher.blockSize)
    rnd.nextBytes(iv)
    val ivParams = IvParameterSpec(iv)
    cipher.init(Cipher.DECRYPT_MODE, key, ivParams)
    val cipherText = cipher.doFinal(dataToDecrypt)
    buildString(cipherText, "decrypt")
    return cipherText
}
private fun generateKey(password: String): SecretKeySpec {
    val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
    val bytes = password.toByteArray()
    digest.update(bytes, 0, bytes.size)
    val key = digest.digest()
    val secretKeySpec = SecretKeySpec(key, "AES")
    return secretKeySpec
}
private fun buildString(text: ByteArray, status: String): String{
    val sb = StringBuilder()
    for (char in text) {
        sb.append(char.toInt().toChar())
    }
   // binding!!.txtViewResult.text = sb.toString()
    return sb.toString()
}