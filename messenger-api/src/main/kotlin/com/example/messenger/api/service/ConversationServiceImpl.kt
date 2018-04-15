package com.example.messenger.api.service

import com.example.messenger.api.exceptions.ConversationIdInvalidException
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import org.springframework.stereotype.Service

@Service
class ConversationServiceImpl(val conversationRepository: ConversationRepository): ConversationService {
    override fun createConversation(userA: User, userB: User): Conversation {
        val conversation = Conversation(userA, userB)
            conversationRepository.save(conversation)
            return conversation
    }

    override fun conversationExists(userA: User, userB: User): Boolean {
        return if(conversationRepository.findBySenderIdAndRecipientId(userA.id,userB.id) != null) true
               else conversationRepository.findBySenderIdAndRecipientId(userB.id,userA.id) != null
    }

    override fun getConversation(userA: User, userB: User): Conversation? {
        return when{
            conversationRepository.findBySenderIdAndRecipientId(userA.id, userB.id) != null ->
                    conversationRepository.findBySenderIdAndRecipientId(userA.id, userB.id)
                    conversationRepository.findBySenderIdAndRecipientId(userB.id, userA.id) != null ->
                            conversationRepository.findBySenderIdAndRecipientId(userB.id, userA.id)
            else -> null
        }
    }

    override fun retriveThread(conversationId: Long): Conversation {
        val conversation = conversationRepository.findById(conversationId)

        if(conversation.isPresent){
            return conversation.get()
        }
        throw ConversationIdInvalidException("Invalid Conversation id '$conversationId'")
    }

    override fun listUserConversations(userId: Long): ArrayList<Conversation> {
        val conversationList: ArrayList<Conversation> = ArrayList()
        conversationList.addAll(conversationRepository.findBySenderId(userId))
        conversationList.addAll(conversationRepository.findByRecipientId(userId))

        return conversationList
    }

    override fun nameSecondParty(conversation: Conversation, userId: Long): String {
        return if(conversation.sender?.id == userId){
            conversation.recipient?.username as String
        }else{
            conversation.sender?.username as String
        }
    }
}