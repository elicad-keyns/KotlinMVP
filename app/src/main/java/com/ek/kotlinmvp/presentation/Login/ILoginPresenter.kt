package com.ek.kotlinmvp.presentation.login

interface ILoginPresenter {
    fun onLogin(email: String, pass: String)
}