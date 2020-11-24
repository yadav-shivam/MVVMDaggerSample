package com.shivam.ecom.buy.quantityselection


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivam.ecom.R
import com.shivam.ecom.common.ext.load
import kotlinx.android.synthetic.main.fragment_image.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ImageFragment : Fragment() {
    private var param1: Int =0
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivProduct.load(param1)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
                ImageFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
