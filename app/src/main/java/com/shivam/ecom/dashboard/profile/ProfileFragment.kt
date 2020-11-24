package com.shivam.ecom.dashboard.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.my_profile_content.*
import javax.inject.Inject


class ProfileFragment : BaseFragment() {


    val TAG = "ProfileFragment"
    @Inject
    lateinit var userStorage: UserStorage
    @Inject
    lateinit var dashboardActivity: DashboardActivity

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appUser = userStorage.get()
        appUser?.let {
            tvPhone.text = it.phoneNumber
            fullNameTv.text = it.fullName
            tvEmail.text= it.email

        }


    }

}