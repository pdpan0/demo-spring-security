package com.pdpano.imc.services

import com.pdpano.imc.models.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/* Classe de Serviço do Usuário, implementa a interface UserDetailsService para autenticação */
@Service
class UserService() : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user_mock: User? = User("lucas", "\$2a\$12\$NhXXpw3fUvK/27kVOC9oV.j1IXWXHXrjdnTBlSw7pwvkufxKN9iri")
        val user = user_mock ?: throw RuntimeException()

        return UserDetail(user)
    }
}