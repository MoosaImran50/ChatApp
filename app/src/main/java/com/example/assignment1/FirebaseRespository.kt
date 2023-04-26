package com.example.assignment1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseRepository(): IFirebaseRepository {

    private val contactsDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Contacts")
    private val messagesDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Messages")

    @Volatile private var INSTANCE: FirebaseRepository ?= null

    fun getInstance(): FirebaseRepository{

        return INSTANCE ?: synchronized(this){
            val instance = FirebaseRepository()
            INSTANCE = instance
            instance
        }

    }

    // reading contacts from database
    override fun readContacts(myContactID: String, contactList: MutableLiveData<List<Contact>>) {

        val contactQuery = contactsDatabaseReference.orderByChild("my_contact_id").equalTo(myContactID)

        contactQuery.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val contacts: List<Contact> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Contact::class.java)!!
                }

                val sortedContacts = contacts.sortedBy { it.receiver_name }
                contactList.postValue(sortedContacts)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        contactsDatabaseReference.keepSynced(true)

    }

    // inserting contact into database
    override fun addContact(contact: Contact) {

        contactsDatabaseReference.child(contact.my_contact_id!!+"-"+contact.receiver_contact_id!!).setValue(contact).addOnSuccessListener {
            Log.d("DatabaseHelper", "Insert Success")
        }.addOnFailureListener{
            Log.d("DatabaseHelper", "Insert Failed")
        }

    }

    // reading messages from database
    override fun readMessages(messageID: String, messageList: MutableLiveData<List<Message>>) {

        val messageQuery = messagesDatabaseReference.orderByChild("message_id").equalTo(messageID)

        messageQuery.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                val messages: List<Message> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Message::class.java)!!
                }

                messageList.postValue(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        messagesDatabaseReference.keepSynced(true)

    }

    // inserting contact into database
    override fun addMessage(message: Message) {

        val newMessageId = messagesDatabaseReference.push().key

        messagesDatabaseReference.child(newMessageId!!).setValue(message).addOnSuccessListener {
            Log.d("DatabaseHelper", "Insert Success")
        }.addOnFailureListener{
            Log.d("DatabaseHelper", "Insert Failed")
        }

    }

}




