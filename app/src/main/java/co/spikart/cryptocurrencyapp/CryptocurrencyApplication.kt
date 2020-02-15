package co.spikart.cryptocurrencyapp

import android.app.Activity
import android.app.Application
import co.spikart.cryptocurrencyapp.di.component.DaggerAppComponent
import co.spikart.cryptocurrencyapp.di.modules.AppModule
import co.spikart.cryptocurrencyapp.di.modules.NetModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class CryptocurrencyApplication: Application(), HasActivityInjector {

  @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .netModule(NetModule(BuildConfig.URL))
        .build().inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}