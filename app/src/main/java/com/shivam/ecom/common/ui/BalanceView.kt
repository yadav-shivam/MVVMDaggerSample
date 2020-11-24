package com.shivam.ecom.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.shivam.ecom.R
import kotlinx.android.synthetic.main.balance_view.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class BalanceView @JvmOverloads constructor(context: Context,
                                            attrs: AttributeSet? = null,
                                            defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var balanceText: String
    @StringRes
    private var currencyText: Int
    private var bgColor : Int

    init {
        View.inflate(context, R.layout.balance_view, this@BalanceView)

        val a = context.theme.obtainStyledAttributes(attrs,
                R.styleable.BalanceView, 0, 0)

        try {
            balanceText = a.getString(R.styleable.BalanceView_balance) ?: ""
            currencyText = a.getResourceId(R.styleable.BalanceView_currency, R.string.default_currency)
            bgColor = a.getResourceId(R.styleable.BalanceView_bgColor, 0)
        } finally {
            a.recycle()
        }

        bind(balanceText, "",0.0f)
    }

    fun bind(balance: String, currencySign: String="",rotation : Float) {
        try {
            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            formatter.applyPattern("#,###.##")
            val s = formatter.format(balance.toDouble())
            balanceTv.text =  s
            ivBg.rotation = rotation
        }catch (e : Exception){
            balanceTv.text =  balance
        }

        balanceTv.append(" $currencySign")
        if (bgColor!=0)
        ivBg.setColorFilter(ContextCompat.getColor(context,bgColor));

//        currencyTv.text = currencySign
    }
}