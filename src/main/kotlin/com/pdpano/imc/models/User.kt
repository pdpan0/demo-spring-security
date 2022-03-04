package com.pdpano.imc.models

data class User(
    val username: String,
    val password: String,
    val role: List<Role>
)