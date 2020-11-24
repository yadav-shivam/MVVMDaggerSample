package com.shivam.ecom.loginregister.register

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.common.Failure
import com.shivam.ecom.common.Loading
import com.shivam.ecom.common.Success
import com.shivam.ecom.common.data.AppUser
import com.shivam.ecom.common.ext.replaceFragment
import com.shivam.ecom.common.file.FileUtils
import com.shivam.ecom.common.file.RealPathUtil
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.DashboardActivity
import com.shivam.ecom.loginregister.LoginRegisterActivity
import com.shivam.ecom.loginregister.login.LoginFragment
import com.httpmangafruit.cardless.common.ext.showToast
import kotlinx.android.synthetic.main.fragment_register.*
import java.io.File
import javax.inject.Inject


/**

 *
 */
class RegisterFragment : BaseFragment() {

    companion object {
        val EXTRA_PHONE_NUMBER = "extra_phone_number"
        val EXTRA_PASSWORD = "extra_password"


        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    private val SELECT_PICTURE_FOR_CAMERA: Int = 120
    private val PERMISSION_CODE_FOR_CAMERA: Int = 121
    private val SELECT_PICTURE_FOR_GALLERY: Int=122
    private val PERMISSION_CODE_FOR_GALLARY: Int= 123
    @Inject
    lateinit var userStorage: UserStorage
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RegisterViewModel
    @Inject
    lateinit var loginRegisterActivity: LoginRegisterActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RegisterViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpannableInLoginText()
        btn_register.setOnClickListener {
            var appUser: AppUser = AppUser("","",nameView.editText?.text.toString(),
                    mobileNumberView.editText?.text.toString(),emailView.editText?.text.toString(),500.0,
                    "INR","en","INR")
                    userStorage.save(appUser)
            userStorage.save(appUser)
            DashboardActivity.starter(activity!!)
            activity!!.finish()
        }
    }

    /*
     * method is used to set the default dialog properties
     * */
    fun setDialogProperties(dialog: Dialog) {
        val window = dialog.window
        var wlp: WindowManager.LayoutParams? = null
        if (window != null) {
            wlp = window.attributes
        }
        val lp = window.attributes
        lp.dimAmount = 0.8f
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (wlp != null) {
            wlp.gravity = Gravity.CENTER
        }
    }

    private fun subscribe() {
        viewModel.data.observe(this, Observer {
            when (it?.status) {
                is Loading -> progressBar.visibility = View.VISIBLE
                is Success -> {
                    btn_register.isEnabled = true

                    progressBar.visibility = View.GONE

                    userStorage.saveCaptchaToken("")

                }
                is Failure -> {
                    btn_register.isEnabled = true

                    progressBar.visibility = View.GONE

                    userStorage.saveCaptchaToken("")
                }
            }
        })
    }

    private fun setSpannableInLoginText(){
        val string1 = getString(R.string.already_have_an_acoun)
        val string2 = getString(R.string.sign_in)

        val spannableString = SpannableString("$string1 $string2")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                loginRegisterActivity.replaceFragment(LoginFragment.newInstance(),R.id.container, addToBackStack = LoginFragment::class.java.simpleName)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color= ContextCompat.getColor(activity!!,android.R.color.black)
            }
        }
        spannableString.setSpan(clickableSpan,spannableString.length-string2.length,spannableString.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSignIn.text= spannableString
        tvSignIn.movementMethod = LinkMovementMethod.getInstance()
    }




    /*
   * check if has permission or not
   * */
    fun hasPermission(permission: String, reqId: Int): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, permission)
        if (result == PackageManager.PERMISSION_GRANTED)
            return true
        else {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(permission), reqId)
            return false
        }
    }

    private var outputUri: Uri? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE_FOR_GALLARY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    showToast(activity!!,R.string.permission_denied,Toast.LENGTH_LONG)
                }
                return
            }
            PERMISSION_CODE_FOR_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    showToast(activity!!,R.string.permission_denied,Toast.LENGTH_LONG)
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_PICTURE_FOR_GALLERY -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val file = File(getPath(activity!!, data.data!!))
                    Glide.with(activity!!).load(Uri.fromFile(file)).into(civUserImage)

                }
            }
            SELECT_PICTURE_FOR_CAMERA -> {
                if (resultCode == Activity.RESULT_OK ) {
                    if (outputUri!=null){
                        val fullPath= getPath(outputUri!!)
                        val file= File(fullPath)
                        Glide.with(activity!!).load(Uri.fromFile(file)).into(civUserImage)

                    }
                }
            }
        }
    }

    /**
     * Method to get path from uri
     *
     * @param uri *
     * @return
     */
    fun getPath(uri: Uri): String {
        return FileUtils.getPath(activity, uri)

    }


    /*
    * get the file path from uri acc to api level
    * */
    @SuppressLint("ObsoleteSdkInt")
    private fun getPath(context: Context, uri: Uri): String? {
        var realPath = String()
        try {
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, uri)
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(context, uri)!!
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(context, uri)
        } catch (e: Exception) {

        }
        return realPath
    }

}