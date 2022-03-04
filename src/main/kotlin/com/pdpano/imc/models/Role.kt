package com.pdpano.imc.models

import org.springframework.security.core.GrantedAuthority

/* Classe de Roles implementando metódo do Sprint Security para autenticação */
data class Role(
    private val name: String = "LEITURA_ESCRITA",
): GrantedAuthority {
    /* Indica a propriedade da Role que irá validar */
    override fun getAuthority() = name
}