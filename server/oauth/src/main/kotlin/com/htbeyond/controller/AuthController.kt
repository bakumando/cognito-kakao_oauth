package com.htbeyond.controller

import com.htbeyond.model.Member
import com.htbeyond.request.RefreshTokenRequest
import com.htbeyond.response.RefreshAuthResponse
import com.htbeyond.service.AuthService
import javax.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @GetMapping("/oauth/kakao")
    fun kakaoLogin(
        @RequestParam code: String,
        response: HttpServletResponse
    ) {
        val (blueId, accessToken, refreshToken)
                = authService.kakaoLogin(code)

        response.sendRedirect(
            "http://localhost:3000/main?blueId=$blueId&accessToken=$accessToken&refreshToken=$refreshToken"
        )
    }

    @GetMapping("/member")
    fun getUser(
        @RequestParam username: String,
    ): ResponseEntity<Member> {
        val result = authService.getUser(username)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/oauth/token/refresh")
    fun refreshAuth(
        @RequestBody request: RefreshTokenRequest,
    ): RefreshAuthResponse {
        return RefreshAuthResponse(access_token = authService.refreshToken(request).accessToken())
    }
}