package com.htbeyond.response

import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType

class CognitoRefreshAuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)

fun AuthenticationResultType.toRefreshAuthResponse() = CognitoRefreshAuthResponse(
    access_token = idToken(),
    token_type = tokenType(),
    expires_in = expiresIn().toLong()
)
