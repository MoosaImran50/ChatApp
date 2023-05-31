package com.example.assignment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel: ViewModel() {

    private lateinit var repository: IFirebaseRepository
    private val messages = MutableLiveData<List<Message>>()

    val allMessages: LiveData<List<Message>> = messages

    fun init(messageID: String) {
        repository = FirebaseRepository().getInstance()
        repository.readMessages(messageID, messages)
    }

}