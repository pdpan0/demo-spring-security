package com.pdpano.imc.config

import com.pdpano.imc.security.JWTAuthenticationFilter
import com.pdpano.imc.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailService: UserDetailsService,
    private val jwtUtil: JWTUtil
) : WebSecurityConfigurerAdapter() {
    /* Configuração do Spring Security para requisições http */
    override fun configure(http: HttpSecurity?) {
        http?.
        /* Desabilita configuração de CSRF */
        csrf()?.disable()?.
        /* Autoriza a requisição */
        authorizeRequests()?.
        /* Adiciona autorização do recurso baseado no perfil criado */
        antMatchers("/greetings")?.hasAuthority("LEITURA_ESCRITA")?.
            mvcMatchers(HttpMethod.POST,"/login")?.permitAll()?.
        /* Qualquer requisição */
        anyRequest()?.
        /* Precisa estar autenticado */
        authenticated()?.
        and()
        /* Adicionando um filtro para autenticação do JWT */
        http?.addFilterBefore(JWTLoginFilter(authManager = authenticationManager(), jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
        http?.addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
        /* Não irá armazenar a sessão do usuário */
        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        /* Desabilita o formulário default do Spring Security para autenticação
            and()?.
            formLogin()?.
            disable()?.
           Comentado pois não é necessário quando o token enviado pelo body.

           -----------------------------------------------------------------

           Identifica qual a forma de autenticação sendo ela Basic
            httpBasic()
           Comentado pois autenticação não é mais do tipo Basic
        */
    }

    /* Responsável pela encriptação da senha do usuário */
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /* Responsável pela autenticação do usuário */
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailService)?.passwordEncoder(bCryptPasswordEncoder())
    }

}