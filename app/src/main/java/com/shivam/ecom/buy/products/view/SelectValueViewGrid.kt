package com.shivam.ecom.buy.products.view

import android.view.View
import com.shivam.ecom.R
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.common.ext.load
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_view_grid.view.*

class SelectValueViewGrid(val selectValue: ProductItemResponse) : Item<ViewHolder>() {

    override fun getLayout(): Int = R.layout.select_value_item_grid

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.apply {
            titleTv.text = selectValue.skuName

            iconIv.load(selectValue.imageUrl)

            isEnabled = selectValue.quantity > 0

            if (isEnabled) {
                section_out_stock.visibility = View.GONE
            } else {
                section_out_stock.visibility = View.VISIBLE

            }

        }
    }
}