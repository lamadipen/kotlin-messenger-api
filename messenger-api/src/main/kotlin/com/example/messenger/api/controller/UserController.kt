package com.example.messenger.api.controller

import com.example.messenger.api.components.UserAssembler
import com.example.messenger.api.helpers.objects.UserListVO
import com.example.messenger.api.helpers.objects.UserVO
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import com.example.messenger.api.service.UserService
import com.example.messenger.api.service.UserServiceImpl
import org.springframework.http.HttpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(val userService: UserServiceImpl,
                     val userAssembler: UserAssembler,
                     val userRepository: UserRepository) {

    @PostMapping
    @RequestMapping("/registrations")
    fun create(@Valid @RequestBody userdetails:User): ResponseEntity<UserVO>{
        val user = userService.attemptRegistration(userdetails)
        return ResponseEntity.ok().body(userAssembler.toUserVO(user!!))
    }

    @GetMapping
    @RequestMapping("/{user_id}")
    fun show(@PathVariable("user_id") userId: Long): ResponseEntity<UserVO>{
        val user = userRepository.findById(userId)
        return ResponseEntity.ok(userAssembler.toUserVO(user.get()))
    }

    @GetMapping
    @RequestMapping("/details")
    fun echoDetials(request: HttpServletRequest): ResponseEntity<UserVO>{
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    fun index(request: HttpServletRequest): ResponseEntity<UserListVO>{
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val users = userService.listUser(user)

        return ResponseEntity.ok(userAssembler.toUserListVO(users))
    }

    fun update(@RequestBody updateDetails: User,
               request: HttpServletRequest): ResponseEntity<UserVO>{
        val currentUser = userRepository.findByUsername(request.userPrincipal.name)
        userService.updateUserStatus(currentUser as User, updateDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(currentUser))
    }
}
