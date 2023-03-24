package com.htbeyond.enums

enum class Gender {
    male, female;

    companion object {
        fun random(): Gender = Gender.values().random()
    }
}