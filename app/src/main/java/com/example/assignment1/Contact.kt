package com.example.assignment1

data class Contact(var my_contact_id: String ?= null,
                   var my_name: String ?= null,
                   var receiver_contact_id: String ?= null,
                   var receiver_name: String ?= null)
