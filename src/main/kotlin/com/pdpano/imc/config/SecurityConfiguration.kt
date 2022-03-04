package com.pdpano.imc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailService: UserDetailsService
) : WebSecurityConfigurerAdapter() {
    /* Configuração do Spring Security para requisições http */
    override fun configure(http: HttpSecurity?) {
        http?.
        /* Autoriza a requisição */
        authorizeRequests()?.
        /* Adiciona autorização do recurso baseado no perfil criado */
        antMatchers("/greetings")?.hasAuthority("LEITURA_ESCRITA")?.
        /* Qualquer requisição */
        anyRequest()?.
        /* Precisa estar autenticado */
        authenticated()?.
        and()?.
        /* Não irá armazenar a sessão do usuário */
        sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.
        and()?.
        /* Desabilita o formulário default do Spring Security para autenticação */
        formLogin()?.
        disable()?.
        /* Identifica qual a forma de autenticação sendo ela Basic */
        httpBasic()
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