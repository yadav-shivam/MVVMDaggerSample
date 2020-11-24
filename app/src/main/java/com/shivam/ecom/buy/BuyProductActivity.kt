package com.shivam.ecom.buy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.shivam.ecom.BaseActivity
import com.shivam.ecom.R
import com.shivam.ecom.buy.quantityselection.PaymentFragment
import com.shivam.ecom.common.ext.replaceFragment
import com.shivam.ecom.dashboard.products.data.ProductItemResponse

class BuyProductActivity : BaseActivity() {

    companion object {
        var paymentFragment: PaymentFragment? = null

        fun starter(context: Context, product: ProductItemResponse) {
            val intent = Intent(context, BuyProductActivity::class.java)
            intent.putExtra("prod",product)
            context.startActivity(intent)
        }
    }

    override fun enableBackButton(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_product_activity)
//        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            paymentFragment= PaymentFragment
                    .newInstance(intent.getSerializableExtra("prod") as ProductItemResponse)
            replaceFragment(paymentFragment!!,
                    R.id.container)
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 202) {
                paymentFragment?.createOrdre(data)
            } else if (data != null) {
                when (requestCode) {
                    203 -> {
                        var reference = data.getStringExtra("Reference")
                        paymentFragment?.confirmCheckout(reference)
                    }
                    204 -> {
                        var reference = data.getStringExtra("Fort_Id")
                        paymentFragment?.confirmCheckout(reference)
                    }
                }

            }
        } else {
            if (requestCode == 203 || requestCode == 204){
                var reason = "Payment Cancelled"
                if (data != null){
                    reason = data.getStringExtra("Reason")
                }
                paymentFragment?.cancelOrder(reason)
            }
        }
    }


}