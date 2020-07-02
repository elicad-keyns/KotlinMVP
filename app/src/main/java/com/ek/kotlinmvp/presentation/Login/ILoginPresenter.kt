package com.ek.kotlinmvp.presentation.Login

interface ILoginPresenter {
    fun onLogin(email: String, pass: String)
}