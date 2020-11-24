package com.shivam.ecom.dashboard.products

import androidx.lifecycle.ViewModel
import com.shivam.ecom.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProductsFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun productsFragment(): ProductsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    internal abstract fun productsViewModel(viewModel: ProductsViewModel): ViewModel
}