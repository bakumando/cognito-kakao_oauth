package com.htbeyond.cognito

import com.auth0.jwt.interfaces.DecodedJWT
import java.time.LocalDate

class Player(
    val username: String,
    val nickname: String,
    val birthdate: String?,
    val clientId: String,
    val email: String,
    val gender: String?
) {
    companion object {
        private const val claimUsername = "cognito:username"
        private const val claimNickName = "nickname"
        private const val claimBirthdate = "birthdate"
        private const val claimGender = "gender"
        private const val claimEmail = "email"
        private const val claimClientId = "aud"

        fun from(jwt: DecodedJWT): Player {
            try {
                return Player(
                    username = jwt.getClaim(claimUsername).asString(),
                    nickname = jwt.getClaim(claimNickName).asString(),
                    birthdate = jwt.getClaim(claimBirthdate).asString(),
                    gender = jwt.getClaim(claimGender).asString(),
                    email = jwt.getClaim(claimEmail).asString(),
                    clientId = jwt.getClaim(claimClientId).asString()
                )
            } catch (e: IllegalStateException) {
                throw Exception("토큰 에러")
            }
        }
    }
}