package com.shivam.ecom.loginregister.login

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.httpmangafruit.cardless.common.ext.createProgressDialog
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.common.Failure
import com.shivam.ecom.common.Loading
import com.shivam.ecom.common.Success
import com.shivam.ecom.common.ext.*
import com.shivam.ecom.dashboard.DashboardActivity
import com.shivam.ecom.loginregister.LoginRegisterActivity
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject


class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var loginRegisterActivity: LoginRegisterActivity

    private lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = createProgressDialog(loginRegisterActivity)

        img_back_press.setImageResource(R.drawable.ic_back)
        observeLogin()

        loginView.setOnClickListener {
            if (mobileView.validateEmpty(getString(R.string.error_required))
                    && passwordView.hasMinLength(getString(R.string.error_password))
            ) {
                viewModel.login(mobileView.getString()
                        , passwordView.getString(), context)
            }
        }

        img_back_press.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun observeLogin() {
        viewModel.data.observe(this, Observer {
            hideKeyboard(loginRegisterActivity)
            when (it?.status) {
                is Success -> {
                    progressDialog.dismiss()
                    DashboardActivity.starter(loginRegisterActivity)
                    loginRegisterActivity.finish()
                }
                is Failure -> {
                    progressDialog.dismiss()

                }
                is Loading -> {
                    progressDialog.show()
                }
            }
        })
    }

}