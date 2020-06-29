package com.ek.kotlinmvp.Presenter

import com.ek.kotlinmvp.Model.User
import com.ek.kotlinmvp.View.ILoginView

class LoginPresenter(internal var ILoginView:ILoginView):ILoginPresenter {
    override fun onLogin(email: String, pass: String) {
        val user = User(email, pass)
        val isLoginSuccess = user.isDataValid
        if (isLoginSuccess)
            ILoginView.onLoginResult("Login Success")
        else
            ILoginView.onLoginResult("Login Error")
    }
}