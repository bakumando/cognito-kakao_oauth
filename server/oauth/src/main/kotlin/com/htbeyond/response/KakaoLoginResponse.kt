package com.htbeyond.response

import com.htbeyond.cognito.Player

class KakaoLoginResponse(
    val idToken: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: String,
    val appClientId: String
) {
}