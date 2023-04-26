package com.example.assignment1

import androidx.lifecycle.MutableLiveData

interface IFirebaseRepository {

    // reading contacts from database
    fun readContacts(myContactID: String, contactList: MutableLiveData<List<Contact>>)

    // inserting contact into database
    fun addContact(contact: Contact)

    // reading messages from database
    fun readMessages(messageID: String, messageList: MutableLiveData<List<Message>>)

    // inserting message into database
    fun addMessage(message: Message)

}