package com.example.messenger.api.service

import com.example.messenger.api.exceptions.MessageEmptyException
import com.example.messenger.api.exceptions.MessageRecipientInvalidException
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.ConversationRepository
import com.example.messenger.api.repositories.MessageRepository
import com.example.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(val messageRepository: MessageRepository,
                         val conversationRepository: ConversationRepository,
                         val conversationService: ConversationService,
                         val userRepository: UserRepository): MessageService {

    override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {
        val optional = userRepository.findById(recipientId)

        if(optional.isPresent){
            val recipient = optional.get()

            if(!messageText.isEmpty()){
                val conversation: Conversation = if(conversationService
                                .conversationExists(sender,recipient)){
                    conversationService.getConversation(sender, recipient) as Conversation
                }else{
                    conversationService.createConversation(sender,recipient)
                }
                conversationRepository.save(conversation)
                val message = Message(sender, recipient, messageText, conversation)
                messageRepository.save(message)
                return message
            }else{
                throw MessageRecipientInvalidException("The recipient id '$recipientId' is invalid")
            }
        }
        throw MessageEmptyException()
    }
}