package com.shivam.ecom.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.shivam.ecom.R
import kotlinx.android.synthetic.main.currancy_view.view.*

class CurrencyView @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val amountText: String
    private val currencyText: String
    private val descriptionText: String

    @StyleRes
    private var amountTextAppearance: Int
    @StyleRes
    private var currencyTextAppearance: Int
    @StyleRes
    private var descriptionTextAppearance: Int

    init {
        View.inflate(context, R.layout.currancy_view, this@CurrencyView)

        val a = context.obtainStyledAttributes(attrs,
                R.styleable.CurrencyView, 0, 0)

        try {
            amountText = a.getString(R.styleable.CurrencyView_amountText) ?: ""
            amountTextAppearance = a.getResourceId(R.styleable.CurrencyView_amountTextAppearance, R.style.TextPrimary_Large)

            currencyText = a.getString(R.styleable.CurrencyView_currencyText) ?: resources.getString(R.string.default_currency)
            currencyTextAppearance = a.getResourceId(R.styleable.CurrencyView_currencyTextAppearance, R.style.TextPrimary_Tiny)

            descriptionText = a.getString(R.styleable.CurrencyView_descriptionText) ?: ""
            descriptionTextAppearance = a.getResourceId(R.styleable.CurrencyView_descriptionTextAppearance, R.style.TextSubhead)
        } finally {
            a.recycle()
        }

        update(amountTv, amountText, amountTextAppearance)
        update(currencyTv, currencyText, currencyTextAppearance)
        updateDescription(descriptionText, descriptionTextAppearance)
    }

    private fun update(textView: TextView, text: String, @StyleRes textAppearance: Int) {
        textView.text = text
        TextViewCompat.setTextAppearance(textView, textAppearance)
    }

    fun updateAmount(text: String) {
        amountTv.text = text
    }

    fun updateCurrency(text: String) {
        currencyTv.text = text
    }

    private fun updateDescription(descriptionText: String, @StyleRes descriptionTextAppearance: Int) {
        if (descriptionText.isNotEmpty()) {
            update(descriptionTv, descriptionText, descriptionTextAppearance)
        } else {
            descriptionTv.visibility = View.GONE
        }
    }
}