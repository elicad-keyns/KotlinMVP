package com.ek.kotlinmvp.Model

interface IUser {
    val email: String
    val pass: String
    val isDataValid: Boolean
}