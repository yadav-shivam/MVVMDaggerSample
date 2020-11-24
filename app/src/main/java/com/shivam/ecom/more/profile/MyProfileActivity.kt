package com.shivam.ecom.more.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.shivam.ecom.BaseActivity
import com.shivam.ecom.R
import com.shivam.ecom.common.storage.UserStorage
import kotlinx.android.synthetic.main.my_profile_activity.*
import kotlinx.android.synthetic.main.my_profile_content.*
import javax.inject.Inject

class MyProfileActivity : BaseActivity() {

    companion object {
        fun starter(context: Context) {
            context.startActivity(Intent(context, MyProfileActivity::class.java))
        }
    }

    @Inject
    lateinit var userStorage: UserStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        val appUser = userStorage.get()
        appUser?.let {
            tvPhone.text = it.phoneNumber
            fullNameTv.text = it.fullName
            tvEmail.text= it.email
            tvBalance.apply {
                val valance =it.currency + " "+it.availableCredit.toString()
                text = valance

            }
        }
    }
}