package com.shivam.ecom.loginregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.shivam.ecom.BaseActivity
import com.shivam.ecom.R
import com.shivam.ecom.common.ext.replaceFragment
import com.shivam.ecom.common.storage.UserStorage
import javax.inject.Inject


class LoginRegisterActivity : BaseActivity() {

    @Inject
    lateinit var userStorage: UserStorage

    companion object {
        fun starter(context: Context) {
            context.startActivity(Intent(context, LoginRegisterActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_register_activity)
/*
        setSupportActionBar(toolbar)
*/

        if (savedInstanceState == null) {
            replaceFragment(LoginRegisterFragment.newInstance(), R.id.container)

        }
    }
}