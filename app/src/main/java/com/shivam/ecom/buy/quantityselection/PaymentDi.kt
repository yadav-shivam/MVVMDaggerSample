package com.shivam.ecom.buy.quantityselection

import androidx.lifecycle.ViewModel
import com.shivam.ecom.buy.products.ProductsViewModel
import com.shivam.ecom.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module abstract class PaymentFragmentBuilder {

  @ContributesAndroidInjector(modules = arrayOf())
  internal abstract fun paymentFragment(): PaymentFragment

  @Binds
  @IntoMap
  @ViewModelKey(PaymentViewModel::class)
  internal abstract fun bindPaymentViewModel(viewModel: PaymentViewModel): ViewModel

  /*@Binds
  @IntoMap
  @ViewModelKey(CategoriesViewModel::class)
  internal abstract fun categoriesViewModel(viewModel: CategoriesViewModel): ViewModel
*/
  @Binds
  @IntoMap
  @ViewModelKey(ProductsViewModel::class)
  internal abstract fun productViewModel(viewModel: ProductsViewModel): ViewModel


}
