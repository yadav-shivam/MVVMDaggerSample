package com.shivam.ecom.buy.cart.views

import com.shivam.ecom.R
import com.shivam.ecom.dashboard.products.data.ProductsResult
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class CartView(val product: ProductsResult) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.cart_item_view

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {

        }
    }
}