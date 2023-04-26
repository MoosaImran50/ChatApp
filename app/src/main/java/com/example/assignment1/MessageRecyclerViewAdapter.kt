package com.example.assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyMessageRecyclerViewAdapter(private val myContactNumber: String, private val myName: String, private val receiverName: String): RecyclerView.Adapter<MyMessageRecyclerViewAdapter.MyViewHolder1>() {

    private val messagesList = ArrayList<Message>()

    companion object {
        private const val LEFT_MESSAGE = 1
        private const val RIGHT_MESSAGE = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = messagesList[position]
        if (message.my_contact_id == myContactNumber) {
            return RIGHT_MESSAGE
        }
        else {
            return LEFT_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View
        if (viewType == LEFT_MESSAGE){
            listItem = layoutInflater.inflate(R.layout.left_list_item, parent, false)
        }
        else {
            listItem = layoutInflater.inflate(R.layout.right_list_item, parent, false)
        }
        return  MyViewHolder1(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        val message = messagesList[position]
        if (message.my_contact_id == myContactNumber){
            holder.bindRight(message, myName)
        }
        else {
            holder.bindLeft(message, receiverName)
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }


    fun updateMessageList(messageList: List<Message>){
        this.messagesList.clear()
        this.messagesList.addAll(messageList)
        notifyDataSetChanged()
    }


    class MyViewHolder1(private val view: View):RecyclerView.ViewHolder(view){
        fun bindLeft(messageText: Message, name: String){
            val userName = view.findViewById<TextView>(R.id.tvUserNameLeft)
            val userMessage = view.findViewById<TextView>(R.id.tvMessageLeft)
            val userTimeStamp = view.findViewById<TextView>(R.id.tvTimeStampLeft)

            userName.text = name
            userMessage.text = messageText.message
            userTimeStamp.text = messageText.time_stamp
        }

        fun bindRight(messageText: Message, name: String){
            val userName = view.findViewById<TextView>(R.id.tvUserNameRight)
            val userMessage = view.findViewById<TextView>(R.id.tvMessageRight)
            val userTimeStamp = view.findViewById<TextView>(R.id.tvTimeStampRight)

            userName.text = name
            userMessage.text = messageText.message
            userTimeStamp.text = messageText.time_stamp
        }

    }

}