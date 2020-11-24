package com.shivam.ecom .buy.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.shivam.ecom.R
import com.shivam.ecom.buy.products.data.CartHeader
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.common.data.AppUser
import com.shivam.ecom.common.ext.fillToHundred
import com.shivam.ecom.common.ext.load
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.cart_header_row.view.*
import kotlinx.android.synthetic.main.cart_item_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(val context: Context, val items: List<Any>?, val appUser: AppUser) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectItemSubject = PublishSubject.create<Int>()
    val clickEventSelect: Observable<Int> = selectItemSubject

    private val removeItemSubject = PublishSubject.create<Int>()
    val clickEventItemRemove: Observable<Int> = removeItemSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==0) {
            val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.cart_item_view,
                            parent, false)
            val cartViewHolder = CartViewHolder(view)
            return cartViewHolder
        }else{
            val view: View = LayoutInflater.from(context)
                    .inflate(R.layout.cart_header_row,
                            parent, false)
            val cartHeaderViewHolder = CartHeaderViewHolder(view)
            return cartHeaderViewHolder
        }

    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        if (items?.get(position) is CartHeader){
            return 1
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder) {
            holder.bind(items?.get(position) as ProductItemResponse)
        }else if (holder is CartHeaderViewHolder){
            holder.bind(items?.get(position) as CartHeader)
        }

    }

    fun notifyRemove(position:Int){
        notifyItemRemoved(position)
    }

    inner class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageRemove:ImageView=view.img_item_minus
        val spinner: AppCompatSpinner =view.spinnerQuantity

        init {
            val quantityList= ArrayList<String>()
            quantityList.fillToHundred()
            val adapter = ArrayAdapter(
                    context, // Context
                    R.layout.spinner_item, // Layout
                    quantityList // Array
            )
            spinner.adapter=adapter
            spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    /*if (numberArray[position].toInt() > (items?.get(adapterPosition) as ProductItemResponse).quantity){
                        showToast(context,R.string.quantity_max_war,Toast.LENGTH_LONG)
                        return
                    }*/
                    (items?.get(adapterPosition) as ProductItemResponse).quantityCart = quantityList[position].toInt()
                    selectItemSubject.onNext(adapterPosition)
                }

            }

            imageRemove.setOnClickListener {
                removeItemSubject.onNext(adapterPosition)
            }
        }

        fun bind(selectValueResponse: ProductItemResponse?) {

            selectValueResponse?.apply {

                view.img_icon.load(imageUrl)
                view.txt_item_name.text = skuName
                if (customAmount>1)
                view.txt_sub_total.text = "SUBTOTAL:" + " " +
                        String.format(Locale.ENGLISH, "%.2f", (totalPrice*customAmount))
                else
                    view.txt_sub_total.text = "SUBTOTAL:" + " " +
                            String.format(Locale.ENGLISH, "%.2f", totalPrice)
                if (appUser != null) {
                    view.txt_sub_total.append(" "+appUser.currentSelectedCurrency)
                }
                view.txt_quantity.text = quantityCart.toString()
                val quantityList= ArrayList<String>()
                quantityList.fillToHundred()
                for (i in 0  until  quantityList.size){
                    if(quantityList[i].toInt() == quantityCart){
                        spinner.setSelection(i)
                        break
                    }
                }

            }

        }

    }

    inner class CartHeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(get: CartHeader?) {
            view.tvHeaderName.text = get?.name
            view.tvCartCount.text = get?.quantity.toString()

        }

    }

}