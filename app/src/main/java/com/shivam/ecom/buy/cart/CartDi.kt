package com.shivam.ecom.buy.cart

import androidx.lifecycle.ViewModel
import com.shivam.ecom.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class CartActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(
            CartFragmentBuilder::class,
            CartModule::class
    ))
    internal abstract fun cartActivity(): CartActivity
}

@Module
abstract class CartFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun cartFragment(): CartFragment

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    internal abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

}

@Module
class CartModule {

}

