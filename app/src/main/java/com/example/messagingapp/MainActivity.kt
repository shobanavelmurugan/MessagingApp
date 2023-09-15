package com.example.messagingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.adapter.CustomAdapter
import com.example.messagingapp.databinding.ActivityMainBinding
import com.example.messagingapp.model.PersonDetails
import com.example.messagingapp.ui.MessageListener
import com.example.messagingapp.ui.MessageReceiver
import com.example.messagingapp.ui.SenderActivity


class MainActivity : AppCompatActivity(), MessageListener {
    companion object{
        val mList = ArrayList<PersonDetails>()
    }
    private lateinit var adapter: CustomAdapter
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        MessageReceiver.bindListener(this)
        initView()
        viewActionClickListener()
        loadList()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.recycleView.layoutManager = layoutManager
        adapter = CustomAdapter(mList)
        // attach adapter to the recycler view
        binding!!.recycleView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadList() {
        adapter.notifyDataSetChanged()
    }

    private fun viewActionClickListener() {
        binding!!.idFABHome.setOnClickListener {
            startActivity(Intent(this, SenderActivity::class.java))
        }
    }

    override fun messageReceived(message: String?) {
        Toast.makeText(this, "New Message Received: $message", Toast.LENGTH_SHORT).show();
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show()
        onSMSReceived(intent.getStringExtra("message"))
    }

    private fun onSMSReceived(stringExtra: String?) {
        Toast.makeText(this, stringExtra, Toast.LENGTH_SHORT).show()
    }
}