package com.htbeyond.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.htbeyond.enums.Gender
import java.time.OffsetDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("MEMBER")
class Member(
    @Id
    val blueId: String,

    var name: String? = null,

    @JsonProperty("phone_number")
    var phone_number: String? = null,

    var email: String? = null,

    var gender: Gender? = null,

    var birthdate: String? = null,

    @Indexed(unique = true, sparse = true)
    var nickname: String,
)