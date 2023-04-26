package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var contactRecyclerView: RecyclerView
    lateinit var adapter: MyContactRecyclerViewAdapter

    lateinit var myName: String
    lateinit var myContact: String

    fun openConversation(myContactID: String, myContactName: String, recipientContactID: String, recipientContactName: String){
        val intent = Intent(this@ContactActivity, ConversationActivity::class.java)
        intent.putExtra("myID", myContactID)
        intent.putExtra("myNAME", myContactName)
        intent.putExtra("receiverID", recipientContactID)
        intent.putExtra("receiverNAME", recipientContactName)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_main)

        // fetching data of the selected contact
        myName = intent.getStringExtra("NAME")!!
        myContact = intent.getStringExtra("CONTACT")!!
        getSupportActionBar()?.setTitle("Welcome $myName !")

        // setting up RecyclerView
        contactRecyclerView = findViewById<RecyclerView>(R.id.ContactsRecyclerView)
        contactRecyclerView.layoutManager = LinearLayoutManager(this)
        contactRecyclerView.setHasFixedSize(true)
        contactRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = MyContactRecyclerViewAdapter()
        contactRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.init(myContact)

        val contactsList = viewModel.allContacts

        contactsList.observe(this, Observer {
            adapter.updateContactList(it)

            contactRecyclerView.scrollToPosition(it.size - 1)
        })

        // overriding RecyclerView item onclick listener function to launch conversation activity
        adapter.setOnItemClickListener(object: MyContactRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

                openConversation(myContact, myName, contactsList.value?.get(position)?.receiver_contact_id!!, contactsList.value?.get(position)?.receiver_name!!)

            }
        })

        val nameTextBox = findViewById<EditText>(R.id.myContactNameTextBox)
        val contactTextBox = findViewById<EditText>(R.id.myContactNumberTextBox)
        val addButton = findViewById<Button>(R.id.addButton)
        val openContactAppButton = findViewById<Button>(R.id.ContactAppButton)

        var enteredName: String
        var enteredNumber: String

        // onclick listener for adding contact button
        addButton.setOnClickListener {
            enteredName = nameTextBox.text.toString().trim()
            enteredNumber = contactTextBox.text.toString().trim()
            if (enteredName == "") {
                Toast.makeText(
                    this@ContactActivity,
                    "Name field can not be left blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (enteredNumber == "") {
                Toast.makeText(
                    this@ContactActivity,
                    "Contact Number field can not be left blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (enteredNumber == myContact) {
                Toast.makeText(
                    this@ContactActivity,
                    "You can not add your own phone number as your contact",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val receiverContact = enteredNumber
                val receiverName = enteredName

                nameTextBox.text.clear()
                contactTextBox.text.clear()

                // writing to database
                viewModel.addContact(Contact(myContact, myName, receiverContact, receiverName))
                viewModel.addContact(Contact(receiverContact, receiverName, myContact, myName))

                openConversation(myContact, myName, receiverContact, receiverName)

            }

        }

        openContactAppButton.setOnClickListener {
            // Launch the contacts application
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT_REQUEST)

        }

    }

    val PICK_CONTACT_REQUEST = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            // Get the contact URI
            val contactUri = data?.data

            // Get the contact name and phone number
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

            val cursor = contentResolver.query(contactUri!!, projection, null, null, null)
            cursor!!.moveToFirst()

            val retrievedName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val retrievedPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            cursor.close()

            viewModel.addContact(Contact(myContact, myName, retrievedPhoneNumber, retrievedName))
            viewModel.addContact(Contact(retrievedPhoneNumber, retrievedName, myContact, myName))

            openConversation(myContact, myName, retrievedPhoneNumber, retrievedName)

        }
    }

}