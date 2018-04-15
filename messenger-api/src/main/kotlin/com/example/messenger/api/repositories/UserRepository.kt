package com.example.messenger.api.repositories

import com.example.messenger.api.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User
    fun findByPhoneNumber(phoneNumber: String): User?

}