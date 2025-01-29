package com.bae.sso.ui

import kotlinx.serialization.Serializable

@Serializable
object SCREEN_LOGIN

@Serializable
data class SCREEN_LOGIN_SUCCESS(
    val email: String,
    val token: String,
    val givenName: String? = null,
    val familyName: String? = null,
    val userImage: String? = null,
    val phoneNumber: String? = null
)

@Serializable
object SCREEN_SIGN_UP