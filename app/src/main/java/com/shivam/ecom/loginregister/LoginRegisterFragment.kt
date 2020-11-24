package com.shivam.ecom.loginregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.common.ext.replaceFragment
import com.shivam.ecom.loginregister.login.LoginFragment
import com.shivam.ecom.loginregister.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login_register.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class LoginRegisterFragment : BaseFragment() {

    companion object {
        fun newInstance(): LoginRegisterFragment {
            return LoginRegisterFragment()
        }
    }

    @Inject
    lateinit var loginRegisterActivity: LoginRegisterActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            loginRegisterActivity.replaceFragment(LoginFragment.newInstance(),
                    R.id.container, addToBackStack = LoginFragment.javaClass.simpleName)
        }

        btn_register.setOnClickListener {
            loginRegisterActivity.replaceFragment(RegisterFragment.newInstance(), R.id.container,
                    addToBackStack = RegisterFragment.javaClass.simpleName)
        }
    }
}
