package com.htbeyond.response

data class KakaoLoginResponse(
    val blueId: String,
    val accessToken: String,
    val refreshToken: String,
)