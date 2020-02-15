package co.spikart.cryptocurrencyapp.di.component

import co.spikart.cryptocurrencyapp.CryptocurrencyApplication
import co.spikart.cryptocurrencyapp.di.modules.AppModule
import co.spikart.cryptocurrencyapp.di.modules.BuildersModule
import co.spikart.cryptocurrencyapp.di.modules.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                AndroidInjectionModule::class,
                BuildersModule::class,
                AppModule::class,
                NetModule::class
        )
)

interface AppComponent {
    fun inject(app: CryptocurrencyApplication)
}