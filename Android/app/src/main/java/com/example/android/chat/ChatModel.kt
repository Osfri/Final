package com.example.android.chat

class ChatModel {
    var users: MutableMap<String, Boolean> = mutableMapOf<String, Boolean>()     // 채팅방 안의 유저들
    var comments: MutableMap<String, Comment> = mutableMapOf<String, Comment>()  // 채팅방의 대화내용들

    class Comment{
        var fromId: String = ""         // 메시지를 보낸 사람
        var message: String = ""        // 메시지의 내용
        lateinit var timestamp: Any     // 메시지를 보낸 시간
        var readUsers:MutableMap<String, Any> = mutableMapOf()  // 메시지를 읽은사람
    }
}