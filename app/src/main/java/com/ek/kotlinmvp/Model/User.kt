package com.ek.kotlinmvp.Model

import android.text.TextUtils
import android.util.Patterns

class User(override val email: String, override val pass: String) : IUser {
    override val isDataValid: Boolean
        get() = (!TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                pass.length > 6)
}