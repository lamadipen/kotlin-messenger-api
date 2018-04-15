package com.example.messenger.api.service

import com.example.messenger.api.exceptions.InvalidUserIdException
import com.example.messenger.api.exceptions.UserStatusEmptyException
import com.example.messenger.api.exceptions.UsernameUnavailableException
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val repository: UserRepository): UserService {

    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetail: User): User? {
       if(!usernameExists(userDetail.username)){
            val user = User()
            user.username = userDetail.username
            user.password = userDetail.password
            user.phoneNumber = userDetail.phoneNumber
            repository.save(user);
            obscurePassword(user)
            return user
       }
       throw UsernameUnavailableException("The Username ${userDetail.username} is unavailable.")
    }

    override fun listUser(currentUser: User): List<User> {
        return repository.findAll().mapTo(ArrayList(), {it})
                .filter { it != currentUser }
    }

    override fun retriveUserData(username: String): User? {
        var user = repository.findByUsername(username)
        obscurePassword(user!!)
        return user
    }
    @Throws(InvalidUserIdException::class)
    override fun retriveUserData(id: Long): User? {
        val optionalUser = repository.findById(id)
        if(optionalUser.isPresent){
            val user = optionalUser.get()
            obscurePassword(user)
            return user
        }
        throw InvalidUserIdException("A user with an id '$id' does not exist")
    }

    override fun usernameExists(username: String): Boolean {
        return repository.findByUsername(username) != null
    }

    @Throws(UserStatusEmptyException::class)
    fun updateUserStatus(currentUser: User, updateDetail: User): User{
        if(!updateDetail.status.isEmpty()){
            currentUser.status = updateDetail.status
            repository.save(currentUser)
            return currentUser
        }
        throw UserStatusEmptyException()
    }

    private fun obscurePassword(user: User) {
        user?.password = "XXX XXX XXX"
    }

}