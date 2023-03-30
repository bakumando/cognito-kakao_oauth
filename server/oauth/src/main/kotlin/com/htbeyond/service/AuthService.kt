package com.htbeyond.service

import com.auth0.jwt.JWT
import com.htbeyond.cognito.Player
import com.htbeyond.enums.Gender
import com.htbeyond.model.Member
import com.htbeyond.repository.MemberRepository
import com.htbeyond.request.RefreshTokenRequest
import com.htbeyond.response.GetTokenResponse
import com.htbeyond.response.KakaoLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.http.Parameters
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType


@Service
@Transactional
class AuthService(
    private val httpClient: HttpClient,
    private val memberRepository: MemberRepository,
    private val cognitoIdentityProviderClient: CognitoIdentityProviderClient
) {

    companion object {
        const val CLIENT_ID = "53nm63nntfpv6mbdvanhldtg1e"
        const val CLIENT_SECRET = "1099lhrbvh15n9o9mkjlosd8inm4h7lu68hr26uupda3iqg74mll"
        const val REDIRECT_URI = "http://localhost:8080/oauth/kakao"
        const val GRANT_TYPE = "authorization_code"
        const val SCOPE = "openid"
    }

    fun kakaoLogin(code: String): KakaoLoginResponse {
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

        val (id_token, access_token, refresh_token) = getTokenResponse

        val parseIdToken = JWT.decode(id_token)
        val player = Player.from(parseIdToken)

        val existsMember = memberRepository.existsById(player.username)
        if (!existsMember) {
            memberRepository.save(
                Member(
                    blueId = player.username,
                    email = player.email,
                    gender = player.gender?.let { Gender.valueOf(it) },
                    birthdate = player.birthdate,
                    nickname = player.nickname,
                )
            )
        }

        return KakaoLoginResponse(
            blueId = player.username,
            accessToken = access_token,
            refreshToken = refresh_token,
        )
    }

    fun refreshToken(request: RefreshTokenRequest): AuthenticationResultType {
        val authResponse = refreshToken(request.blueId, request.refresh_token)
        return authResponse.authenticationResult()
    }

    private fun refreshToken(username: String, refreshToken: String) =
        cognitoIdentityProviderClient.initiateAuth {
            it.authFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
                .clientId(CLIENT_ID)
                .authParameters(
                    mapOf(
                        "REFRESH_TOKEN" to refreshToken,
                        "SECRET_HASH" to calculateSecretHash(username)
                    )
                )
        }

    fun getUser(username: String): Member? {
        return memberRepository.findByBlueId(username)
    }

    fun calculateSecretHash(usernaame: String): String? {
        val HMAC_SHA256_ALGORITHM = "HmacSHA256"
        val signingKey = SecretKeySpec(
            CLIENT_SECRET.toByteArray(StandardCharsets.UTF_8),
            HMAC_SHA256_ALGORITHM
        )
        return try {
            val mac = Mac.getInstance(HMAC_SHA256_ALGORITHM)
            mac.init(signingKey)
            mac.update(usernaame.toByteArray(StandardCharsets.UTF_8))
            val rawHmac = mac.doFinal(CLIENT_ID.toByteArray(StandardCharsets.UTF_8))
            Base64.getEncoder().encodeToString(rawHmac)
        } catch (e: Exception) {
            throw RuntimeException("Error while calculating ")
        }
    }
}