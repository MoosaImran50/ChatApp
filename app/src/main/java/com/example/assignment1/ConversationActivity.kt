package com.example.assignment1

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class ConversationActivity : AppCompatActivity() {

    private lateinit var viewModel: MessageViewModel
    private lateinit var messageRecyclerView: RecyclerView
    lateinit var adapter: MyMessageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // fetching data of the selected contact
        val myContactNumber = intent.getStringExtra("myID")
        val myContactName = intent.getStringExtra("myNAME")
        val receiverContactNumber = intent.getStringExtra("receiverID")
        val receiverContactName = intent.getStringExtra("receiverNAME")
        getSupportActionBar()?.setTitle(receiverContactName)

        // Assigning unique ID to this conversation
        // It will be used by both two retrieve messages from database(firebase)
        val messageID: String
        if(myContactNumber!! > receiverContactNumber!!){
            messageID = myContactNumber+"-"+receiverContactNumber
        }
        else{
            messageID = receiverContactNumber+"-"+myContactNumber
        }

        // setting up RecyclerView
        messageRecyclerView = findViewById<RecyclerView>(R.id.MessagesRecyclerView)
        messageRecyclerView.setBackgroundColor(Color.BLACK)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.setHasFixedSize(true)
        adapter = MyMessageRecyclerViewAdapter(myContactNumber, myContactName!!, receiverContactName!!)
        messageRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.init(messageID)

        val messagesList = viewModel.allMessages

        messagesList.observe(this, androidx.lifecycle.Observer {
            adapter.updateMessageList(it)

            messageRecyclerView.scrollToPosition(it.size - 1)
        })

        val textBox = findViewById<EditText>(R.id.myTextBox)
        val sendButton = findViewById<Button>(R.id.mySendButton)
        var enteredMessage: String

        // setting onclick listener for message sending button
        sendButton.setOnClickListener {
            enteredMessage = textBox.text.toString().trim()
            if (enteredMessage == "") {
                Toast.makeText(
                    this@ConversationActivity,
                    "Text field can not be left blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val message = enteredMessage
                val timeFormat = SimpleDateFormat("h:mm a")
                val currentTime = timeFormat.format(Date())

                textBox.text.clear()

                // writing to database
                Message(messageID, myContactNumber, receiverContactNumber, message, currentTime).addMessage()
            }
        }

    }
}