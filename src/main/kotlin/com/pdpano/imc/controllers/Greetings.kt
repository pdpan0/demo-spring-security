package com.pdpano.imc.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Greetings {
    @GetMapping("/greetings")
    fun greetings() = ResponseEntity.ok("Olá amigo.")

    @PostMapping("/login")
    fun login() = ResponseEntity.ok("token")
}