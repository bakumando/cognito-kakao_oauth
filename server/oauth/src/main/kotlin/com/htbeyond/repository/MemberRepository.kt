package com.htbeyond.repository

import com.htbeyond.model.Member
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member, String> {
}