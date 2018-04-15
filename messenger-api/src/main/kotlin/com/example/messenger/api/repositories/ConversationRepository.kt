package com.example.messenger.api.repositories

import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConversationRepository: CrudRepository<Conversation, Long> {
    fun findBySenderId(id: Long): List<Conversation>
    fun findByRecipientId(id: Long): List<Conversation>
    fun findBySenderIdAndRecipientId(senderId: Long, recipientId: Long): Conversation?
}