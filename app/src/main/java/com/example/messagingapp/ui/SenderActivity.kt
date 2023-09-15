package com.example.messagingapp.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.messagingapp.MainActivity
import com.example.messagingapp.databinding.ActivitySenderBinding
import com.example.messagingapp.model.PersonDetails
import com.example.messagingapp.utils.AES

class SenderActivity: AppCompatActivity()  {

    private var binding: ActivitySenderBinding? = null
    private val MY_PERMISSIONS_REQUEST_SEND_SMS=26

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewActionClickListener()
    }

    private fun viewActionClickListener() {
        binding!!.btnSendSMS.setOnClickListener {
            if(binding!!.edtViewText.text!!.trim().isNotEmpty() &&
                binding!!.edtViewPhoneNumber.text!!.trim().isNotEmpty()) {
                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                            arrayOf(android.Manifest.permission.SEND_SMS),
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }

                }
                AES.setKey("password")
                AES.encrypt(binding!!.edtViewText.text.toString())
                binding!!.txtViewResult.text= AES.getEncryptedString()
                sendSMS(binding!!.edtViewPhoneNumber.text.toString(),AES.getEncryptedString())
            }else{
                Toast.makeText(this,"phone number or message empty",Toast.LENGTH_SHORT).show()
            }
        }
        binding!!.btnBack.setOnClickListener {  startActivity(Intent(this, MainActivity::class.java)) }

        /*
           binding!!.btnEncrypt.setOnClickListener {
            AES.setKey("password")
            AES.encrypt(binding!!.edtViewText.text.toString())
            binding!!.txtViewResult.text= AES.getEncryptedString()
        }
        binding!!.btnDecrypt.setOnClickListener {
            val strToDecrypt =  AES.getEncryptedString()
            AES.decrypt(strToDecrypt.trim())
            binding!!.txtViewResult.text=AES.getDecryptedString()
        }*/
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"),
            PendingIntent.FLAG_IMMUTABLE)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
        MainActivity.mList.add(PersonDetails(phoneNumber.toLong(),message))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()
            AES.setKey("password")
            AES.encrypt(binding!!.edtViewText.text.toString())
            binding!!.txtViewResult.text= AES.getEncryptedString()
            sendSMS(binding!!.edtViewPhoneNumber.text.toString(),AES.getEncryptedString())
        } else {
            Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}