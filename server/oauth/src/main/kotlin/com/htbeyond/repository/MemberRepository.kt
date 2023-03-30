package com.htbeyond.repository

import com.htbeyond.model.Member
import java.util.Optional
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member, String> {

    fun findByBlueId(blueId: String): Member?
}