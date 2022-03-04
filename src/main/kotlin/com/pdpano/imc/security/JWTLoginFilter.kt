package com.pdpano.imc.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.pdpano.imc.config.JWTUtil
import com.pdpano.imc.models.Credentials
import com.pdpano.imc.services.UserDetail
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(
    /* Gerenciador da autenticação */
    private val authManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {

    /* Metódo responsável pela tentativa de autenticação */
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        /* Recebe e mapeia as informações do usuário que vieram na requisição */
        val (username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
        /* Válida o username e password do usuário para autenticação */
        val token = UsernamePasswordAuthenticationToken(username, password)

        return authManager.authenticate(token)
    }

    /* Metódo responsável pela devolução do token no header, é chamado quando o usuário
     * realiza a autenticação
     */
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user = (authResult?.principal as UserDetail)
        val token = jwtUtil.generateToken(user.username, user.authorities)
        response?.addHeader("Authorization", "Bearer $token")
    }
}
