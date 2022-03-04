package com.pdpano.imc.services

import com.pdpano.imc.models.Role
import com.pdpano.imc.models.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/* Classe de Serviço do Usuário, implementa a interface UserDetailsService para autenticação */
@Service
class UserService() : UserDetailsService {

    /* Método de consulta ao usuário da base */
    override fun loadUserByUsername(username: String?): UserDetails {

        val user_mock: User? = User(
            "lucas",
            "\$2a\$12\$B4Yynlq81WaMxsXEChtQ7uQPpxN7lhsWvROUtnO9aLNMcapZtxJ46",
            listOf(Role("LEITURA_ESCRITA"))
        )

        val user = user_mock ?: throw RuntimeException()

        return UserDetail(user)
    }
}