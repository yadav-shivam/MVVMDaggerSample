package com.shivam.ecom.buy.thankyou

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.shivam.ecom.BaseFragment
import com.shivam.ecom.R
import com.shivam.ecom.buy.BuyProductActivity
import com.shivam.ecom.common.ext.getColoredString
import kotlinx.android.synthetic.main.thank_you_fragment.*
import javax.inject.Inject

class ThankYouFragment : BaseFragment() {

  @Inject lateinit var buyProductActivity: BuyProductActivity

  companion object {

    private val EXTRA_CHECKOUT = "EXTRA_CHECKOUT"

    fun newInstance(transactionId: String): ThankYouFragment {
      val fragment = ThankYouFragment()
      val bundle = Bundle()
      bundle.putString(EXTRA_CHECKOUT, transactionId)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun isUpEnabled(): Boolean = true

  override fun title(): Int {
    return R.string.title_thank_you
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.thank_you_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val checkout = savedInstanceState?.getString(EXTRA_CHECKOUT)

    val orderMessage = SpannableStringBuilder(getText(R.string.order_ordered_successfully_pt1))
      .append(getColoredString(" #2123123 ", ContextCompat.getColor(buyProductActivity, R.color.colorAccent)))
      .append(getText(R.string.order_ordered_successfully_pt2))

    orderMessageTv.text = orderMessage

    orderCountAndTypeTv.text = getString(R.string.order_count_type, 13, "iTunes US $50")

    currentBalanceView.apply {
      updateAmount("1000")
      updateCurrency(getString(R.string.default_currency))
    }

    buyAgainBtn.setOnClickListener {

    }
  }
}