package com.example.assignment1

data class Message(var message_id: String ?= null,
                   var my_contact_id: String ?= null,
                   var receiver_contact_id: String ?= null,
                   var message: String ?= null,
                   var time_stamp: String ?= null)
