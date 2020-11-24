package com.shivam.ecom.dashboard.more

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.httpmangafruit.cardless.common.ext.showDialog
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.DashboardActivity
import com.shivam.ecom.loginregister.LoginRegisterActivity
import com.shivam.ecom.more.profile.MyProfileActivity
import kotlinx.android.synthetic.main.more_fragment.*
import javax.inject.Inject

class MoreFragment : BaseFragment() {


    val TAG = "MoreFragment"
    val METHOD_X_REPORT = "XReport"
    val METHOD_Z_REPORT = "ZReport"
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory



    @Inject
    lateinit var userStorage: UserStorage
    @Inject
    lateinit var dashboardActivity: DashboardActivity


    companion object {
        fun newInstance(): MoreFragment {
            return MoreFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.more_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appUser = userStorage.get()

        myProfileTv.setOnClickListener {
            activity?.let { MyProfileActivity.starter(it) }
        }


        logoutBtn.setOnClickListener {
            if (appUser?.token == null)
                moveToLogin()
            else
                showLogoutDialog()
        }
    }


    private fun moveToLogin() {
        activity?.let { LoginRegisterActivity.starter(it) }
        dashboardActivity.finish()
    }

    private fun showLogoutDialog() {
        activity?.let {
            showDialog(
                    context = it,
                    message = getString(R.string.more_logout_message),
                    buttonText = R.string.title_logout,
                    callback = DialogInterface.OnClickListener { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        userStorage.remove()
                        LoginRegisterActivity.starter(dashboardActivity)
                        dashboardActivity.finish()

                    }).show()
        }
    }


}