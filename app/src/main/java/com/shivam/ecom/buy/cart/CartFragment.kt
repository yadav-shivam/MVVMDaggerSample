package com.shivam.ecom.buy.cart


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.httpmangafruit.cardless.common.ext.showToastException
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.buy.cart.adapter.CartAdapter
import com.shivam.ecom.buy.products.data.CartHeader
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.common.Failure
import com.shivam.ecom.common.Loading
import com.shivam.ecom.common.Success
import com.shivam.ecom.common.ext.plusAssign
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.common.ui.CurrencyView
import com.shivam.ecom.loginregister.LoginRegisterActivity
import com.shivam.ecom.loginregister.login.data.Paymentmethod
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.thank_you_fragment.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 *
 */
class CartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CartViewModel
    @Inject
    lateinit var cartActivity: CartActivity

    @Inject
    lateinit var userStorage: UserStorage
    private var respTransactionId = "";
    private var payFortAmount = 0.0;


    lateinit var cartAdapter: CartAdapter
    lateinit var list: MutableList<Any>
    var paymentmethod: MutableList<Paymentmethod>? = null
    var showPaymentMethod: Boolean = false

    var selectedPaymentMethod = "3"
    var selectedPaymentMethodName = "My Balance"

    //var reason = "Cancelled because of exception"
    var reason = ""

    val disposables = CompositeDisposable()

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CartViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appUser = userStorage.get()

        if (appUser != null) {
            tvCurrency.text = appUser.currentSelectedCurrency
        }
        list = ArrayList<Any>()
        cartAdapter = CartAdapter(activity!!, list, appUser!!)
        initRecyclerView()
        setupClickLister()

        if (userStorage.getPaymentmethod() != null) {
            paymentmethod = userStorage.getPaymentmethod()!!
            var paymentmethodTemp = userStorage.getPaymentmethod()!!

            for (payment in paymentmethodTemp!!) {
                if (!payment.availableinreorder)
                    paymentmethod!!.remove(payment)
            }

            if (paymentmethod!!.size > 0)
                showPaymentMethod = true
        }

        tvCheckout.setOnClickListener { procedToCheckOutBtn.performClick() }
        procedToCheckOutBtn.setOnClickListener {

            val appUser = userStorage.get()
            if (appUser?.token != null) {

                if (list.size > 0) {
                    procedToCheckOutBtn.isEnabled = false
                    /*showDialog(activity!!,"Success","Order successfull...",R.string.common_ok, object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            userStorage.removeCart()
                            activity?.finish()
                        }

                    }).show()*/
                    showOrderPlacedDialog()
                } else {
                    showToastException(cartActivity, "Can not proceed with empty cart")
                }
            } else {
                activity?.let { LoginRegisterActivity.starter(it) }
            }
        }
        ivBack.setOnClickListener { activity?.onBackPressed() }

        userStorage.get()?.apply {
        }

    }


    private fun setupClickLister() {

        disposables += cartAdapter.clickEventSelect
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val selectValueResponse =
                            list.get(it) as ProductItemResponse

                    /*Check quantity */
                    if (selectValueResponse.quantityCart >= 1) {

                        selectValueResponse.quantityCart =
                                selectValueResponse.quantityCart

                        selectValueResponse.totalPrice =
                                selectValueResponse.cost *
                                        selectValueResponse.quantityCart

                        list.set(it, selectValueResponse)
                        cartAdapter.notifyDataSetChanged()

                        val oneItemPrice: Double = selectValueResponse.totalPrice /
                                selectValueResponse.quantityCart
                        viewModel.removeFromTotal(oneItemPrice)

                        val copyList: MutableList<ProductItemResponse> =
                                mutableListOf<ProductItemResponse>()
                        for (item in list){
                            if (item is ProductItemResponse){
                                copyList.add(item)
                            }
                        }
                        viewModel.updateCart(copyList)

                    }

                }

        disposables += cartAdapter.clickEventItemRemove
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    val selectValueResponse =
                            list.get(it) as ProductItemResponse

                    val totalPrice = selectValueResponse.totalPrice
                    list.remove(selectValueResponse)
                    cartAdapter.notifyItemRemoved(it)

                    viewModel.removeFromTotal(totalPrice)


                    val copyList: MutableList<ProductItemResponse> =
                            mutableListOf<ProductItemResponse>()
                    for (item in list){
                        if (item is ProductItemResponse){
                            copyList.add(item)
                        }
                    }
                    viewModel.updateCart(copyList)

                }

    }

    private fun initRecyclerView() {

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(cartActivity,
                    LinearLayoutManager.VERTICAL))
            adapter = cartAdapter


        }
        val itemAnimator = DefaultItemAnimator()
        recyclerView.itemAnimator = itemAnimator!!
        recyclerView.getItemAnimator()!!.setRemoveDuration(3000);
    }

    override fun onResume() {
        super.onResume()
        viewModel.balance()
        viewModel.loadCart()
    }

    private fun subscribeUi() {

        viewModel.data.observe(this, androidx.lifecycle.Observer {
            when (it?.status) {
                is Loading ->
                    progressBar.visibility=View.VISIBLE
                is Success -> {
                    procedToCheckOutBtn.isEnabled = true

                    userStorage.removeCart()
                    progressBar.visibility=View.GONE
                    ///to do
                    //goToOrderDetails()
                    respTransactionId = it.data!![0].transactionId
                    viewModel.confirmCheckout(respTransactionId, "Pay123456", selectedPaymentMethodName)


                }
                is Failure -> {
                    procedToCheckOutBtn.isEnabled = true

                    progressBar.visibility=View.GONE
                }
            }
        })


        viewModel.data3.observe(this, androidx.lifecycle.Observer {
            when (it?.status) {
                is Loading ->                     progressBar.visibility=View.VISIBLE

                is Success -> {
                    progressBar.visibility=View.GONE

                    //goToOrderDetails(it.data!!)
                    alertDailog(reason)
                }
                is Failure -> {
                    progressBar.visibility=View.GONE
                }
            }
        })

        viewModel.balance.observe(this, androidx.lifecycle.Observer {

            val user = userStorage.get()

            it?.let {

            }
        })
        viewModel.cart?.observe(this, androidx.lifecycle.Observer {
            val hashMap: HashMap<String,MutableList<ProductItemResponse>> = HashMap()
            list.clear()
            if (it != null && it.size > 0) {
                for (item in viewModel.cart?.value!!){
                    if(hashMap.containsKey(item.category)){
                        hashMap[item.category]?.add(item)
                    }else{
                        val list= ArrayList<ProductItemResponse>()
                        list.add(item)
                        hashMap.put(item.category,list)
                    }
                }
            }
            for ((key, value) in hashMap) {
                var count =0
                for (item in value){
                    count+=item.quantityCart
                }
                val cartHeader= CartHeader(key, count)
                list.add(cartHeader)
                list.addAll(value)
            }


            if (list.isNotEmpty()) {

                llCheckoutAndRefCode.visibility = View.VISIBLE
                tvCheckout.visibility = View.VISIBLE
                secion_total.visibility = View.VISIBLE
                tv_continue_buy_card.visibility = View.VISIBLE

                viewModel.calculateTotal()
            } else {

                llCheckoutAndRefCode.visibility = View.INVISIBLE
                tvCheckout.visibility = View.INVISIBLE
                secion_total.visibility = View.INVISIBLE
                //todo show message of no item in cart
                tv_continue_buy_card.text = getString(R.string.there_is_no_item_in_cart)
                tv_continue_buy_card.visibility = View.VISIBLE
            }


            cartAdapter.notifyDataSetChanged()
        })

        viewModel.total.observe(this, androidx.lifecycle.Observer {
            cardCostTv.updateAmount(String.format(Locale.ENGLISH,
                    "%.2f", it))
            updateCurreny(cardCostTv)

            payFortAmount = it!!

        })
    }


    private fun updateCurreny(currencyView: CurrencyView) {
        val appUser = userStorage.get()
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

    }


    fun result(requestCode: Int, resultCode: Int, data: Intent) {

    }

    fun createOrder(data: Intent?) {
        if (data != null) {
            selectedPaymentMethod = data.getStringExtra("Payment Method")
            selectedPaymentMethodName = data.getStringExtra("Payment Method Name")
            showPaymentMethod = false
        }
        viewModel.checkout(etReferralCode.text.toString().trim())
    }

    fun confirmCheckout(reference: String) {
        viewModel.confirmCheckout(respTransactionId, reference, selectedPaymentMethodName)
    }

    fun cancelOrder(reason: String) {

    }

    private fun alertDailog(message: String) {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(activity)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.lbl_alert))

        // Make Alert non cancelable
        builder.setCancelable(false)

        // Display a message on alert dialog
        builder.setMessage(message)

        // Set a positive button and its click listener on alert dialog
        /*builder.setPositiveButton("YES") { dialog, which ->
            // Do something when user press the positive button
        }*/

        builder.setNegativeButton("OK") { dialog, which ->
            activity?.finish()
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun showOrderPlacedDialog(){
        val dialog : Dialog = Dialog(activity)
        dialog.apply {
            window.requestFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.thank_you_fragment)
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            orderMessageTv.text="Order was done successfully!"
            orderCountAndTypeTv.text = ""
            setCancelable(false)
            buyAgainBtn.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    userStorage.removeCart()
                    activity?.finish()
                }

            })
            show()
        }
    }
}
