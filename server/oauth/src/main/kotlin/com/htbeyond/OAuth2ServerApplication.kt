package com.htbeyond

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2ClientServerApplication

fun main(args: Array<String>) {
    runApplication<OAuth2ClientServerApplication>(*args)
}