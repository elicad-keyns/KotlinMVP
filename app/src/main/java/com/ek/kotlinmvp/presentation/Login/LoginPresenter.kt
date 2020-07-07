package com.ek.kotlinmvp.presentation.login

import com.ek.kotlinmvp.data.local.login.User

class LoginPresenter(internal var ILoginView: ILoginView):
    ILoginPresenter {
    override fun onLogin(email: String, pass: String) {
        val user = User(email, pass)
        if (user.email.isNotEmpty() && user.pass.length >= 6)
            ILoginView.onLoginResult("Login Success")
        else
            ILoginView.onLoginResult("Login Error")
    }
}