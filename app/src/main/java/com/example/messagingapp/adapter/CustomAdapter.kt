package com.example.messagingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.databinding.ListItemBinding
import com.example.messagingapp.model.PersonDetails
import com.example.messagingapp.utils.AES

class CustomAdapter(
    var mList: List<PersonDetails>,
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.txtViewPhoneNUmber.text = this.phoneNumber.toString()
                AES.setKey("password")
                AES.setEncryptedString(this.message)
                AES.decrypt(this.message.trim())
                binding.txtViewMessage.text = AES.getDecryptedString()
            }
        }
    }

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
}
