package com.htbeyond.request

data class UpdateMemberRequest(
    val name: String,
    val birthdate: String,
    val phone_number: String
) {
}