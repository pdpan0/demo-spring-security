package com.pdpano.imc.config

import com.pdpano.imc.models.Role
import com.pdpano.imc.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

/* Classe responsável pela geração do token JWT */
@Component
class JWTUtil(
    private val userService: UserService
) {
    private val expiration: Long = 60000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    /* Método para geração do token JWT
     * Obs: Para geração do token caso o esteja usando uma versão superior do Java 9
     * É necessário aplicar a seguinte dependência:
     * implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    */
    fun generateToken(username: String, authorities: List<Role>): String? {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    /* Método para validação do Token */
    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    /* Metódo abre o token JWT e retorna a autenticação do usuário */
    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val user = userService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}