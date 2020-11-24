package com.shivam.ecom.buy.quantityselection

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.httpmangafruit.cardless.common.ext.createProgressDialog
import com.httpmangafruit.cardless.common.ext.showToast
import com.httpmangafruit.cardless.common.ext.showToastException
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.buy.BuyProductActivity
import com.shivam.ecom.buy.cart.CartActivity
import com.shivam.ecom.buy.products.ProductsViewModel
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.buy.quantityselection.data.CreateOrderItem
import com.shivam.ecom.common.Failure
import com.shivam.ecom.common.Loading
import com.shivam.ecom.common.Success
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.loginregister.LoginRegisterActivity
import com.shivam.ecom.loginregister.login.data.Paymentmethod
import kotlinx.android.synthetic.main.payment_fragment_updated.*
import javax.inject.Inject

class PaymentFragment : BaseFragment() {

    var selectedFilter: String=""
    private lateinit var selectedSubCategory: String
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var productsViewModel: ProductsViewModel

    @Inject
    lateinit var buyProductActivity: BuyProductActivity
    @Inject
    lateinit var userStorage: UserStorage

    var mainMenu: Menu? = null
    var paymentmethod: MutableList<Paymentmethod>? = ArrayList()
    var showPaymentMethod: Boolean = false

    private lateinit var productItem : ProductItemResponse


    var selectedPaymentMethod = "3"
    var selectedPaymentMethodName = "My Balance"

    var reason = "Cancelled because of exception"

    private val progressDialog by lazy {
        createProgressDialog(buyProductActivity)
    }


    private var respTransactionId = ""
    private var payFortAmount = 0.0

    companion object {
        private val EXTRA_QUANTITY_KEY = "EXTRA_QUANTITY_KEY"

        fun newInstance(prod: com.shivam.ecom.dashboard.products.data.ProductItemResponse): PaymentFragment {
            val paymentFragment = PaymentFragment()
            val bundle = Bundle()
            var productItemResponse=ProductItemResponse(prod.name,prod.name,prod.name,prod.quantityCart,
                    prod.price.toDouble(),prod.price.toDouble(),prod.imageUrl,false,"",prod.quantityCart,prod.price.toDouble(),prod.price)
            bundle.putParcelable("prod",productItemResponse)
            paymentFragment.arguments = bundle
            return paymentFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        paymentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PaymentViewModel::class.java)
        productsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ProductsViewModel::class.java)

    }

    override fun title(): Int {
        return R.string.title_quantity_selection
    }

    override fun isUpEnabled(): Boolean = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.payment_fragment_updated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appUser = userStorage.get()
        val productItemResponse : ProductItemResponse = arguments?.getParcelable("prod")!!
        productItem= productItemResponse
        tvCost.text= productItemResponse.cost.toString()
        ivMinus.setOnClickListener { paymentViewModel.quantity-- }
        ivPlus.setOnClickListener { paymentViewModel.quantity++ }
        ivCartHeader.setOnClickListener {
            if (appUser?.token == null) {
                loginAlertDailog()
                return@setOnClickListener
            }
            CartActivity.starter(buyProductActivity)
        }
        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        changeShoppingCartIcon()

//        observerAmountEt()
//        incrementAmountIv.setOnClickListener { paymentViewModel.amount++ }
//        decrementAmountIv.setOnClickListener { paymentViewModel.amount-- }

        if (userStorage.getPaymentmethod() != null) {
            paymentmethod = userStorage.getPaymentmethod()!!
            var paymentmethodTemp = userStorage.getPaymentmethod()!!

            for (payment in paymentmethodTemp) {
                if (!payment.availableinreorder)
                    paymentmethod!!.remove(payment)
            }

            if (paymentmethod!!.size > 0)
                showPaymentMethod = true
        }

        llCheckoutBtn.setOnClickListener {
            if (appUser?.token == null) {
                loginAlertDailog()
                return@setOnClickListener
            }
            if (userStorage.isCartEmpty()) {
                val appUser = userStorage.get()
                if (appUser?.token != null) {
                    if (paymentmethod!!.size > 0) {

                    } else {
                        val createOrderItem = CreateOrderItem(productItem.sku
                                ?: "", paymentViewModel.quantity, paymentViewModel.amount)
                        paymentViewModel.checkout(createOrderItem)
                    }
                } else {
                    activity?.let { LoginRegisterActivity.starter(it) }
                }

            } else {
                /*selectValue!!.quantityCart = paymentViewModel.quantity
                    userStorage.saveIntoCart(selectValueResponse = selectValue!!)*/

                CartActivity.starter(buyProductActivity)
//                buyProductActivity.finish()
            }
        }

        llAddToCart.setOnClickListener {
            if (appUser?.token == null) {
                loginAlertDailog()
                           } else {
                if (productItem != null) {
                    productItem!!.quantityCart = paymentViewModel.quantity
                    productItem!!.customAmount = paymentViewModel.amount
                    userStorage.saveIntoCart(selectValueResponse = productItem!!)
                }
                activity?.let { showToastException(it, getString(R.string.item_has_been_added)) }
                changeShoppingCartIcon()

            }

        }

        setCategoryViewPager(productItemResponse)
//        CategoriesDataHolder.dispose()

        if(appUser?.token==null){
            flCart.visibility=View.GONE

        }else{
            flCart.visibility=View.VISIBLE
        }

    }

    fun loginAlertDailog( ) {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(activity)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.please))
        // Display a message on alert dialog
        builder.setMessage(getString(R.string.login_to_perform))
        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.login_caps)) { dialog, which ->
            // Do something when user press the positive button
            LoginRegisterActivity.starter(activity as Context)
            activity?.finish()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }



    override fun onResume() {
        super.onResume()
        changeShoppingCartIcon()
    }

    private fun updateCurreny(currencyView: TextView) {
        val appUser = userStorage.get()

        if (appUser != null) {
            currencyView.append(appUser.currentSelectedCurrency)
        } else {
            currencyView.append("")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_QUANTITY_KEY, paymentViewModel.quantity)
    }


    private fun subscribeUi() {

        paymentViewModel.totalAmountData.observe(this, Observer {
            updateTotalAmountTextView(it ?: 0.0)
        })

        paymentViewModel.quantityData.observe(this, Observer {
            updateQuantityTextView(it ?: 0)
        })

        paymentViewModel.customAmountData.observe(this, Observer {
            updateAmountTextView(it ?: 0)
        })

        paymentViewModel.data.observe(this, Observer {
            when (it?.status) {
                is Loading -> progressDialog.show()
                is Success -> {
                    progressDialog.hide()
                    respTransactionId = it.data!![0].transactionId

                    /*
                    *If there is no payment method, proceed without payfort and use balance instead.
                     */
                    
                        paymentViewModel.confirmCheckout(respTransactionId, "Pay123456", selectedPaymentMethodName)


                }
                is Failure -> {
                    progressDialog.dismiss() }
            }
        })

        paymentViewModel.data2.observe(this, Observer {
            when (it?.status) {
                is Loading -> progressDialog.show()
                is Success -> {
                    progressDialog.hide()
                }
                is Failure -> {
                    progressDialog.dismiss()
                }
            }
        })

        paymentViewModel.data3.observe(this, Observer {
            when (it?.status) {
                is Loading -> progressDialog.show()
                is Success -> {
                    progressDialog.hide()

                    //goToOrderDetails(it.data!!)
                    alertDailog(reason)

                }
                is Failure -> {
                    progressDialog.dismiss()
                }
            }
        })



        productsViewModel.data.observe(this, Observer {
            when (it?.status) {
                is Loading -> changeProgressBarVisibility(true)
                is Success -> {
                    changeProgressBarVisibility(false)
                    tvCardsNo.text = "1"
                }
                is Failure -> {
                    changeProgressBarVisibility(false)
                    activity?.let { showToast(it) }
//                    activity?.finish()
                }
            }

        })

        productsViewModel.filteredData.observe(this, Observer {
            tvCardsNo.text = "1"
        })
    }

    private fun changeProgressBarVisibility(show: Boolean) {
        progressBar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }




    private fun updateQuantityTextView(quantity: Int) {
        tvCardsNo.setText(quantity.toString())
    }

    private fun updateAmountTextView(amount: Int) {
//        amountEt.setText(amount.toString())
    }

    private fun updateTotalAmountTextView(totalAmount: Double) {
        payFortAmount = totalAmount

        tvTotal.text = totalAmount.toString()
        updateCurreny(tvTotal)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_products, menu)
        val actionSearch = menu?.findItem(R.id.action_search)
        actionSearch?.isVisible = false
        return super.onCreateOptionsMenu(menu, inflater)
    }


    private fun changeShoppingCartIcon() {
        if (userStorage.isCartEmpty()) {
            tvCartCount.visibility=View.GONE
        } else {
            tvCartCount.visibility=View.VISIBLE
            val list = userStorage.getCart()
            var total =0
            for (item in list.orEmpty()){
               total+=item.quantityCart
            }
            tvCartCount.text = total.toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun createOrdre(data: Intent?) {
        if (data != null) {
            selectedPaymentMethod = data.getStringExtra("Payment Method")
            selectedPaymentMethodName = data.getStringExtra("Payment Method Name")
            showPaymentMethod = false
        }
        val createOrderItem = CreateOrderItem(productItem.sku
                ?: "", paymentViewModel.quantity, paymentViewModel.amount)
        paymentViewModel.checkout(createOrderItem)
    }

    fun cancelOrder(reason: String) {
        this@PaymentFragment.reason = reason
        paymentViewModel.cancelOrder(respTransactionId, reason)
    }

    fun confirmCheckout(reference: String) {
        paymentViewModel.confirmCheckout(respTransactionId, reference, selectedPaymentMethodName)
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


    private fun setCategoryViewPager(prod: ProductItemResponse){
        vpCategories.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ImageFragment.newInstance(prod.imageUrl,"")
            }

            override fun getCount(): Int {
                return 1
            }


        }
    }


    fun refreshProducts(subCategory: String, filter: String){
        selectedSubCategory= subCategory
        productsViewModel.getValues(subCategory,filter,true)
    }
}