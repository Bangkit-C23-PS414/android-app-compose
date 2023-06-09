package com.bangkit.coffee.presentation.signup.components

import me.naingaungluu.formconductor.annotations.EmailAddress
import me.naingaungluu.formconductor.annotations.MinLength

data class SignUpForm(
    @MinLength(1)
    val name: String = "",

    @EmailAddress
    val email: String = "",

    @MinLength(8)
    val password: String = "",

    @SignUpConfirmPassword
    val confirmPassword: String = "",
)
