package com.shivam.ecom.buy.cart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.shivam.ecom.BaseActivity
import com.shivam.ecom.R
import com.shivam.ecom.buy.BuyProductActivity
import com.shivam.ecom.common.ext.replaceFragment

class CartActivity : BaseActivity() {


    companion object {

        var cartFragment : CartFragment? = null

        fun starter(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        if (savedInstanceState == null) {
            cartFragment = CartFragment.newInstance()
            replaceFragment(cartFragment!!, R.id.container)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 202) {
                cartFragment!!.createOrder(data)
            } else if (data != null) {
                if (requestCode == 203) {
                    var reference = data.getStringExtra("Reference")
                    cartFragment!!.confirmCheckout(reference)
                }else if (requestCode == 204) {
                    var reference = data.getStringExtra("Fort_Id")
                    BuyProductActivity.paymentFragment!!.confirmCheckout(reference)
                } else {
                    cartFragment!!.result(requestCode, resultCode, data)
                }

            }
        } else{
            if (requestCode == 203 || requestCode == 204){
                var reason = "Payment Cancelled"
                if (data != null){
                    reason = data.getStringExtra("Reason")
                }
                cartFragment!!.cancelOrder(reason)
            }
        }
    }
}
