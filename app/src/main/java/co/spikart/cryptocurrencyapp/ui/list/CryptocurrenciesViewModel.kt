package co.spikart.cryptocurrencyapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.spikart.cryptocurrencyapp.data.Cryptocurrency
import co.spikart.cryptocurrencyapp.data.source.CryptocurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject


class CryptocurrenciesViewModel @Inject constructor(
    private val cryptocurrencyRepository: CryptocurrencyRepository) : ViewModel() {

  var cryptocurrenciesResult: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
  var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
  var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()
  lateinit var disposableObserver: DisposableObserver<List<Cryptocurrency>>

  fun cryptocurrenciesResult(): LiveData<List<Cryptocurrency>> {
    return cryptocurrenciesResult
  }

  fun cryptocurrenciesError(): LiveData<String> {
    return cryptocurrenciesError
  }

  fun cryptocurrenciesLoader(): LiveData<Boolean> {
    return cryptocurrenciesLoader
  }

  fun loadCryptocurrencies(limit: Int, offset: Int ) {

    disposableObserver = object : DisposableObserver<List<Cryptocurrency>>() {
      override fun onComplete() {

      }

      override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
        cryptocurrenciesResult.postValue(cryptocurrencies)
        cryptocurrenciesLoader.postValue(false)
      }

      override fun onError(e: Throwable) {
        cryptocurrenciesError.postValue(e.message)
        cryptocurrenciesLoader.postValue(false)
      }
    }

    cryptocurrencyRepository.getCryptocurrencies(limit, offset)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .debounce(400, MILLISECONDS)
        .subscribe(disposableObserver)
  }

  fun disposeElements(){
    if (!disposableObserver.isDisposed) disposableObserver.dispose()
  }

}