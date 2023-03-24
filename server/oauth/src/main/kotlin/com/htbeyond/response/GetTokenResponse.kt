package com.htbeyond.response

data class GetTokenResponse(
    val id_token: String,
    val access_token: String,
    val refresh_token: String,
    val expires_in: Long,
    val token_type: String
) {

}

