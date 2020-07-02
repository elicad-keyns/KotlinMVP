package com.ek.kotlinmvp.presentation.Login

import com.ek.kotlinmvp.data.local.User
import com.ek.kotlinmvp.presentation.Login.ILoginPresenter
import com.ek.kotlinmvp.presentation.Login.ILoginView

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