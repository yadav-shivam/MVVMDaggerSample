package com.shivam.ecom.dashboard.products

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.buy.BuyProductActivity
import com.shivam.ecom.buy.cart.CartActivity
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.DashboardActivity
import com.shivam.ecom.dashboard.products.data.ProductItemResponse
import com.shivam.ecom.dashboard.products.views.ProductView
import com.shivam.ecom.dashboard.products.views.ProductViewGrid
import com.shivam.ecom.loginregister.LoginRegisterActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.UpdatingGroup
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.common_list_balance.*
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProductsViewModel
    @Inject
    lateinit var dashboardActivity: DashboardActivity
    @Inject
    lateinit var userStorage: UserStorage

    private val section = Section()
    private val updatingGroup = UpdatingGroup()
    var mainMenu: Menu? = null
    var actionViewChange: MenuItem? = null
    private val groupAdapter = GroupAdapter<ViewHolder>()
    var isChangeView = false
    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(ProductsViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.common_list_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBack.visibility = View.GONE
        initRecyclerView()
        filterWatcher()
        section.add(updatingGroup)
        groupAdapter.add(section)

        swipeContainer.setOnRefreshListener {
            section.remove(updatingGroup)
            viewModel.products(showLoading = false)
            section.add(updatingGroup)
        }
        ivGrid.setOnClickListener {
            userStorage.toggleViewType()

            setLayoutManager()
            populateAdapter(viewModel.data.value!!)
        }
        ivCart.setOnClickListener {
            if(userStorage.get()!=null)
            CartActivity.starter(dashboardActivity)
            else alertDailog(2)

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.balance()
        if (userStorage.getViewType() == UserStorage.GRID) {
            ivGrid?.setImageResource(R.mipmap.ic_list)
        } else {
            ivGrid?.setImageResource(R.mipmap.ic_grid)

        }
        changeShoppingCartIcon()
        checkAndChangeView()
    }

    fun alertDailog( type: Int) {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(activity)

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
            LoginRegisterActivity.starter(activity!!)
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


    private fun checkAndChangeView() {
        if (isChangeView) {
            isChangeView = false
            if (recyclerView != null && recyclerView.layoutManager != null) {
                if (userStorage.getViewType() == UserStorage.GRID) {
                    if (recyclerView.layoutManager is LinearLayoutManager) {
                        changeView()
                    }
                } else if (userStorage.getViewType() == UserStorage.LIST) {
                    if (recyclerView.layoutManager is GridLayoutManager) {
                        changeView()

                    }
                }
            }
        }


    }

    private fun changeView() {
        setLayoutManager()
        if (viewModel.data.value != null)
            populateAdapter(viewModel.data.value!!)
    }


    private fun filterWatcher() {

        ed_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                s?.let { viewModel.filter(ed_filter.text.toString()) }
            }
        })
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        actionViewChange = menu?.findItem(R.id.action_view_change)


        mainMenu = menu


    }

    private fun changeShoppingCartIcon() {
        if (userStorage.isCartEmpty()) {
            ivCart?.setImageResource(R.mipmap.ic_shopping_cart)
        } else {
            ivCart?.setImageResource(R.mipmap.ic_cart_blue_filled)
        }
    }


    private fun subscribeUi() {
        viewModel.data.observe(this, Observer {
                    val appUser = userStorage.get()
                    stopRefreshing()
                    progressBar.visibility = View.GONE
                    viewModel.balance()
                    populateAdapter(it)

        })
    }

    private fun stopRefreshing() {
        if (swipeContainer.isRefreshing) {
            swipeContainer.isRefreshing = false;
        }
    }

    private fun initRecyclerView() {

        groupAdapter.setOnItemClickListener { item, _ ->
            if (item is ProductViewGrid) {
                val product = item.product
                chkQtyStartActivity(product)
            } else if (item is ProductView) {
                val product = item.product
                chkQtyStartActivity(product)
            }
        }
        setLayoutManager()


    }

    private fun chkQtyStartActivity(product: ProductItemResponse) {

        /*     if (product.qty != 0) {*/
        BuyProductActivity.starter(dashboardActivity, product)

        /*   }*/
    }

    private fun setLayoutManager() {
        if (userStorage.getViewType() == UserStorage.GRID) {
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = groupAdapter
            }

        } else {

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = groupAdapter
            }

        }
    }

    private fun populateAdapter(list: List<ProductItemResponse>) {

        if (list.size > 0) {
            updatingGroup.update(list.map {

                if (userStorage.getViewType() == UserStorage.GRID) {

                    ProductViewGrid(it)

                } else {

                    ProductView(it)
                }
            }
            )
        }
    }
}