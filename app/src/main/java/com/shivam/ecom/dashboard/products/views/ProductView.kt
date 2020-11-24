package com.shivam.ecom.dashboard.products.views

import com.shivam.ecom.R
import com.shivam.ecom.common.ext.load
import com.shivam.ecom.dashboard.products.data.ProductItemResponse
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_view.view.*

class ProductView(val product: ProductItemResponse) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.product_view

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.apply {

            iconIv.load(product.imageUrl)
            titleTv.text = product.name
        }
    }
}