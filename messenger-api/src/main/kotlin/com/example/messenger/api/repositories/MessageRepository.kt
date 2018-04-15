package com.example.messenger.api.repositories

import com.example.messenger.api.models.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: CrudRepository<Message, Long> {
    fun findByConversationId(conversationId: Long): List<Message>
}