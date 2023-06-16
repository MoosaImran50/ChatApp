# ChatApp

![chat](https://github.com/MoosaImran50/ChatApp/assets/108832275/2fd9183d-7130-455e-82d1-af694ccb3f39)

ChatApp is a realtime messaging/chatting Android app developed using Kotlin. The app uses Firebase Realtime Database for storage.

## Login Screen

The login screen allows users to enter their name and phone number.

## Main Components

### Contacts Activity

The Contacts Activity maintains the list of contacts for the user. Users can initiate conversations in two ways:

1. By entering the name and phone number of the contact.
2. By launching the phone's default contact app, selecting a contact, and returning to the "ChatApp" application using an implicit intent.

Users can select any contact from the list to open a conversation.

### Messaging Activity

The Messaging Activity allows users to send and receive messages in real-time. Sent and received messages are differentiated by color, with sent messages displayed in green and received messages in yellow. Each message bubble displays the user's name, timestamp, and message content.

Messages are fetched and displayed in real-time without any delay.

## Demo Video

https://github.com/MoosaImran50/ChatApp/assets/108832275/fd08b6ab-e6ea-483b-9751-2e227ddde097

## Screenshots

<img src="https://github.com/MoosaImran50/ChatApp/assets/108832275/09f431fd-59a1-46a7-9293-b512a45b5bbc" alt="Screenshot 1" width="300">
<img src="https://github.com/MoosaImran50/ChatApp/assets/108832275/b2dae34f-4938-44c1-b996-000556fb9b33" alt="Screenshot 2" width="300">
<img src="https://github.com/MoosaImran50/ChatApp/assets/108832275/b17a0b6e-fa82-4463-bda5-8632b5c2648a" alt="Screenshot 3" width="300">

