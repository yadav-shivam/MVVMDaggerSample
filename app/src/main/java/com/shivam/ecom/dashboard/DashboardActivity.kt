package com.shivam.ecom.dashboard

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.shivam.ecom.BaseActivity
import com.shivam.ecom.R
import com.shivam.ecom.common.ext.replaceFragment
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.products.ProductsFragment
import com.shivam.ecom.dashboard.profile.ProfileFragment
import com.shivam.ecom.loginregister.LoginRegisterActivity
import kotlinx.android.synthetic.main.dashboard_content.*
import javax.inject.Inject

class DashboardActivity : BaseActivity() {

    @Inject
    lateinit var userStorage: UserStorage

    private var selectedBottomItem =1


    companion object {
        fun starter(context: Context) {
            context.startActivity(Intent(context, DashboardActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val appUser = userStorage.get()
        userStorage.saveFirstTime("false")
        setContentView(R.layout.dashboard_activity)
//        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            replaceFragment(ProductsFragment.newInstance(), R.id.container)
        }

        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_home_vector))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_power_settings_new_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_profile_vector))

        bottomNavigation.show(1)
        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    selectedBottomItem = it.id
                    replaceFragment(ProductsFragment.newInstance(), R.id.container)
                }
                2 -> {
                    selectedBottomItem = it.id
                    com.httpmangafruit.cardless.common.ext.showDialog(this,"Logout","Are you sure you want to logout ?",
                            R.string.ok,object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            userStorage.remove()
                            LoginRegisterActivity.starter(this@DashboardActivity)
                            finish()
                        }

                    },R.string.cancel,object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                        }

                    }).show()
                }
                3 -> {

                }
                4 -> {
                    val appUser = userStorage.get()
                    if (appUser?.token == null) {
                        alertDailog(2)
                    } else {
                        selectedBottomItem = it.id
                        replaceFragment(ProfileFragment.newInstance(), R.id.container)
                    }
                }
            }
        }

    }


    fun alertDailog( type: Int) {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.please))
        // Display a message on alert dialog
        when(type){
            2-> {
                builder.setMessage(getString(R.string.login_to_check_profile))
            }
            3->{
                builder.setMessage("Login to select products.")
            }
        }

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.login_caps)) { dialog, which ->
            // Do something when user press the positive button
            LoginRegisterActivity.starter(this)
            finish()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            bottomNavigation.show(selectedBottomItem,false)
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }
}
