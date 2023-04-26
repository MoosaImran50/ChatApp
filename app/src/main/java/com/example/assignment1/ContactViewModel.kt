package com.example.assignment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel: ViewModel() {

    private lateinit var repository: IFirebaseRepository
    private val contacts = MutableLiveData<List<Contact>>()

    val allContacts: LiveData<List<Contact>> = contacts

    fun init(myContactID: String) {
        repository = FirebaseRepository().getInstance()
        repository.readContacts(myContactID, contacts)
    }

    fun addContact(contact: Contact) {
        repository.addContact(contact)
    }

}