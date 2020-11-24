package com.shivam.ecom.buy.products.view

import android.view.View
import androidx.core.widget.TextViewCompat
import com.shivam.ecom.R
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.common.ext.load
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_view.view.*
import kotlinx.android.synthetic.main.select_value_item.view.statusTv
import kotlinx.android.synthetic.main.select_value_item.view.valueTv

class SelectValueView(val selectValue: ProductItemResponse): Item<ViewHolder>() {

  override fun getLayout(): Int = R.layout.select_value_item

  override fun bind(viewHolder: ViewHolder, position: Int) {

    viewHolder.itemView.apply {
      valueTv.text = selectValue.skuName

      isEnabled = selectValue.quantity > 0

      iconIv.load(selectValue.imageUrl)


      if (isEnabled) {

        img_arrow.alpha = 1.0f
        iconIv.alpha = 1.0f
        TextViewCompat.setTextAppearance(valueTv, R.style.TextPrimary_Medium)
        statusTv.visibility = View.GONE
      } else {
        iconIv.alpha = 0.5f
        img_arrow.alpha = 0.5f
        TextViewCompat.setTextAppearance(valueTv, R.style.TextDisabled_Medium)
        statusTv.visibility = View.VISIBLE
      }
    }
  }
}