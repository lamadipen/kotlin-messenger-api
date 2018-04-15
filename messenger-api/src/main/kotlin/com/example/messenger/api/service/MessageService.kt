package com.example.messenger.api.service

import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User
import org.springframework.stereotype.Service

 interface MessageService {
    fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}