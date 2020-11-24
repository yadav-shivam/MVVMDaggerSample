/*
package com.httpmangafruit.cardless.buy.selectvalue

import android.content.Context
import android.os.Bundle
import android.view.*
import com.httpmangafruit.cardless.BaseFragment
import com.httpmangafruit.cardless.R
import com.httpmangafruit.cardless.buy.BuyProductActivity
import com.httpmangafruit.cardless.buy.cart.CartActivity
import com.httpmangafruit.cardless.buy.quantityselection.PaymentFragment
import com.httpmangafruit.cardless.buy.selectvalue.data.ProductItemResponse
import com.httpmangafruit.cardless.buy.selectvalue.view.SelectValueView
import com.httpmangafruit.cardless.buy.selectvalue.view.SelectValueViewGrid
import com.httpmangafruit.cardless.common.Failure
import com.httpmangafruit.cardless.common.Loading
import com.httpmangafruit.cardless.common.Success
import com.httpmangafruit.cardless.common.ext.showToast
import com.httpmangafruit.cardless.common.storage.UserStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.UpdatingGroup
import com.xwray.groupie.ViewHolder
import javax.inject.Inject

class SelectValueFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProductsViewModel
    @Inject
    lateinit var userStorage: UserStorage
    @Inject
    lateinit var buyProductActivity: BuyProductActivity
    var category = ""
    private val groupAdapter = GroupAdapter<ViewHolder>()

    private val section = Section()
    private val updatingGroup = UpdatingGroup()
    var mainMenu: Menu? = null
    var actionViewChange: MenuItem? = null

    companion object {
        private const val categoryKey = "category"
        var isChangeView = false

        fun newInstance(category: String): SelectValueFragment {
            val selectValueFragment = SelectValueFragment()
            val bundle = Bundle()
            bundle.putString(categoryKey, category)
            selectValueFragment.arguments = bundle
            return selectValueFragment
        }
    }

    override fun title(): Int {
        return R.string.title_select_value
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
    }

    override fun isUpEnabled(): Boolean = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeToLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(categoryKey) ?: ""
        viewModel.getValues(category)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.common_list_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initRecyclerView()

        }
        if (!(section.groupCount > 0)) {
            section.add(updatingGroup)
            groupAdapter.add(section)
        }

        val appUser = userStorage.get()
        if (appUser?.token == null)
            balanceView.visibility = View.GONE
        else
            balanceView.visibility = View.VISIBLE

        swipeContainer.setOnRefreshListener {

            section.remove(updatingGroup)
            viewModel.getValues(category, false)
            section.add(updatingGroup)
        }

    }

    override fun onResume() {
        super.onResume()
        onPrepareOptionsMenu(menu = mainMenu)
    }

    private fun stopRefreshing() {
        if (swipeContainer.isRefreshing) {
            swipeContainer.isRefreshing = false;
        }
    }

    private fun subscribeToLiveData() {
        viewModel.data.observe(this, Observer {
            when (it?.status) {
                is Loading -> changeProgressBarVisibility(true)
                is Success -> {
                    changeProgressBarVisibility(false)
                    viewModel.balance()
                    populateAdapter(it.data.orEmpty())
                    stopRefreshing()
                }
                is Failure -> {
                    stopRefreshing()
                    changeProgressBarVisibility(false)
                    activity?.let { showToast(it) }
                    activity?.finish()
                }
            }
        })

        viewModel.balance.observe(this, Observer {
            val user = userStorage.get()

            it?.let {

                if (user?.currentSelectedCurrency != null)
                    balanceView.bind(it, user.currentSelectedCurrency)
                else
                    balanceView.bind(it)

            }
        })
    }

    private fun initRecyclerView() {
        groupAdapter.setOnItemClickListener { item, _ ->
            if (item is SelectValueView) {
                BuyProductActivity.paymentFragment = PaymentFragment.newInstance(item.selectValue)
                replaceFragment(
                        BuyProductActivity.paymentFragment!!,
                        R.id.container,
                        SelectValueFragment::class.java.simpleName)
            } else if (item is SelectValueViewGrid) {
                BuyProductActivity.paymentFragment = PaymentFragment.newInstance(item.selectValue)
                replaceFragment(
                        BuyProductActivity.paymentFragment!!,
                        R.id.container,
                        SelectValueFragment::class.java.simpleName)
            }
        }
        setLayoutManager()
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
                addItemDecoration(DividerItemDecoration(buyProductActivity,
                        LinearLayoutManager.VERTICAL))
                adapter = groupAdapter
            }

        }
    }


    private fun populateAdapter(list: List<ProductItemResponse>) {
        if (list.size > 0) {
*/
/*
            val updatingGroup = UpdatingGroup()
*//*

            updatingGroup.update(list.map {

                if (userStorage.getViewType() == UserStorage.GRID) {

                    SelectValueViewGrid(it)
                } else {

                    SelectValueView(it)
                }
            })
*/
/*
            groupAdapter.add(updatingGroup)
*//*

        }
    }

    private fun changeProgressBarVisibility(show: Boolean) {
        progressBar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_cart, menu)
        actionViewChange = menu?.findItem(R.id.action_view_change)

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        if (userStorage.getViewType() == UserStorage.GRID) {
            actionViewChange?.setIcon(R.mipmap.ic_list)
        } else {
            actionViewChange?.setIcon(R.mipmap.ic_grid)

        }

        changeShoppingCartIcon(menu)
        mainMenu = menu

    }

    private fun changeShoppingCartIcon(menu: Menu?) {
        val actionCart = menu?.findItem(R.id.action_cart)
        if (userStorage.isCartEmpty()) {
            actionCart?.setIcon(R.mipmap.ic_shopping_cart)
        } else {
            actionCart?.setIcon(R.mipmap.ic_cart_blue_filled)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_change -> {
                isChangeView = true
                userStorage.toggleViewType()
                buyProductActivity.invalidateOptionsMenu()

                setLayoutManager()
                populateAdapter(viewModel.data.value?.data!!)
            }
            R.id.action_cart -> {
                CartActivity.starter(buyProductActivity)
            }
        }
        return super.onOptionsItemSelected(item)

    }

}*/
