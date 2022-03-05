package com.pdpano.imc.controllers

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Greetings(
    @Value("\${pdpano.imc.buildNumber}") private val buildNumber: String
) {
    @GetMapping("/greetings")
    fun greetings() = ResponseEntity.ok(buildNumber)

    @PostMapping("/login")
    fun login() = ResponseEntity.ok("token")
}