package com.pdpano.imc.services

import com.pdpano.imc.models.User
import org.springframework.security.core.userdetails.UserDetails

/* Responsável por fazer a comunicação do Spring Security e válidação do usuário */
class UserDetail(
    private val user: User
) : UserDetails {
    override fun getAuthorities() = null

    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}