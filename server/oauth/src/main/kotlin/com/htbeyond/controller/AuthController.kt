package com.htbeyond.controller

import com.htbeyond.model.Member
import com.htbeyond.request.RefreshTokenRequest
import com.htbeyond.request.UpdateMemberRequest
import com.htbeyond.response.RefreshAuthResponse
import com.htbeyond.service.AuthService
import javax.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
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

    @PostMapping("/oauth/token/refresh")
    fun refreshAuth(
        @RequestBody request: RefreshTokenRequest,
    ): RefreshAuthResponse {
        return RefreshAuthResponse(access_token = authService.refreshToken(request).accessToken())
    }


    // 유저 추가 프로필 업데이트 테스트용
    @PutMapping("/member/{blueId}")
    fun updateMember(
        @PathVariable blueId: String,
        @RequestBody request: UpdateMemberRequest,
        @RequestHeader("Authorization") accessToken: String
    ): Member {
        return authService.updateMember(blueId, accessToken, request)
    }
}