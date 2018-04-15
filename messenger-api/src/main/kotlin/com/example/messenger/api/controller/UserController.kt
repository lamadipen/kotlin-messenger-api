package com.example.messenger.api.controller

import com.example.messenger.api.models.User
import com.example.messenger.api.service.UserService
import com.example.messenger.api.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(val userService: UserServiceImpl) {

    @PostMapping
    @RequestMapping("/registrations")
    fun create(@Valid @RequestBody userdetails:User): ResponseEntity<User>{
        val user = userService.attemptRegistration(userdetails)
        return ResponseEntity.ok().body(user)
    }
}