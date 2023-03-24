package com.htbeyond.controller

import com.htbeyond.response.KakaoLoginResponse
import com.htbeyond.service.KakaoAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val kakaoAuthService: KakaoAuthService
) {

    @GetMapping("kakao/oauth")
    fun kakaoLogin(
        @RequestParam code: String,
    ): KakaoLoginResponse {
        return kakaoAuthService.kakaoLogin(code)
    }
}