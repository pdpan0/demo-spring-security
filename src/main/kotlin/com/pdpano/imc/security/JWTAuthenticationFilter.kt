package com.pdpano.imc.security

import com.pdpano.imc.config.JWTUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    private val jwtUtil: JWTUtil
) : OncePerRequestFilter() {

    /* Metódo responsável pela filtro (autenticação do token) das requisições */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")
        val jwt = getTokenDetail(token)
        if (jwtUtil.isValid(jwt)) {
            /* Consulta a autenticação do usuário */
            val authentication = jwtUtil.getAuthentication(jwt)
            /* Adiciona à autenticação ao contexto da aplicação */
            SecurityContextHolder.getContext().authentication = authentication
        }

        /* Realiza o filtro da aplicação */
        filterChain.doFilter(request, response)
    }

    /* Apenas para remover o tipo Bearer do header */
    private fun getTokenDetail(token: String?): String? {
        return token?.let { jwt ->
            jwt.startsWith("Bearer ")
            jwt.substring(7, jwt.length)
        }
    }
}
