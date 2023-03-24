package com.htbeyond.service

import com.auth0.jwt.JWT
import com.htbeyond.cognito.Player
import com.htbeyond.enums.Gender
import com.htbeyond.model.Member
import com.htbeyond.repository.MemberRepository
import com.htbeyond.response.GetTokenResponse
import com.htbeyond.response.KakaoLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.http.Parameters
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class KakaoAuthService(
    private val httpClient: HttpClient,
    private val memberRepository: MemberRepository
) {

    companion object {
        const val CLIENT_ID = "53nm63nntfpv6mbdvanhldtg1e"
        const val CLIENT_SECRET = "1099lhrbvh15n9o9mkjlosd8inm4h7lu68hr26uupda3iqg74mll"
        const val REDIRECT_URI = "http://localhost:8080/kakao/oauth"
        const val GRANT_TYPE = "authorization_code"
        const val SCOPE = "openid"
    }

    fun kakaoLogin(code: String): KakaoLoginResponse {
        println(code)

        val getTokenResponse = runBlocking {
            httpClient.post<GetTokenResponse>("https://custom-development.auth.ap-northeast-2.amazoncognito.com/oauth2/token") {
                body = FormDataContent(
                    Parameters.build {
                        append("grant_type", GRANT_TYPE)
                        append("client_id", CLIENT_ID)
                        append("client_secret", CLIENT_SECRET)
                        append("code", code)
                        append("scope", SCOPE)
                        append("redirect_uri", REDIRECT_URI)
                    })
            }
        }

        val (id_token, access_token, refresh_token, expires_in, token_type) = getTokenResponse

        val parseIdToken = JWT.decode(getTokenResponse.id_token)
        val player = Player.from(parseIdToken)

        memberRepository.save(
            Member(
                blueId = player.username,
                email = player.email,
                gender = player.gender?.let { Gender.valueOf(it) },
                birthdate = player.birthdate,
                nickname = player.nickname,
            )
        )
        return KakaoLoginResponse(
            idToken = id_token,
            accessToken = access_token,
            refreshToken = refresh_token,
            expiresIn = expires_in,
            tokenType = token_type,
            appClientId = player.clientId
        )
    }
}