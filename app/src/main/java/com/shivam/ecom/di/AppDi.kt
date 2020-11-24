package com.shivam.ecom.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.shivam.ecom.EcomApp
import com.shivam.ecom.buy.BuyProductBuilder
import com.shivam.ecom.buy.cart.CartActivityBuilder
import com.shivam.ecom.dashboard.DashboardActivityBuilder
import com.shivam.ecom.di.modules.ViewModelBuilder
import com.shivam.ecom.loginregister.LoginRegisterActivityBuilder
import com.shivam.ecom.more.profile.MyProfileActivityBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelBuilder::class,
        LoginRegisterActivityBuilder::class,
        DashboardActivityBuilder::class,
        BuyProductBuilder::class,
        MyProfileActivityBuilder::class,
        CartActivityBuilder::class))


interface AppComponent : AndroidInjector<EcomApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<EcomApp>()
}

@Module
class AppModule {

    @Provides
    fun context(ecomApp: EcomApp): Context = ecomApp.applicationContext

    @Singleton
    @Provides
    fun preferences(app: EcomApp): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}