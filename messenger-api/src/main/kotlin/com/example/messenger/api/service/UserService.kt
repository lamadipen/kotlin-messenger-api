package com.example.messenger.api.service

import com.example.messenger.api.models.User

interface UserService{
    fun attemptRegistration(userDetail : User): User?
    fun listUser(currentUser: User): List<User>
    fun retriveUserData(username: String): User?
    fun retriveUserData(id: Long): User?
    fun usernameExists(username: String): Boolean

}