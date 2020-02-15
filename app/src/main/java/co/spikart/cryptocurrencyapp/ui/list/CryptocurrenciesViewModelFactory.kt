package co.spikart.cryptocurrencyapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class CryptocurrenciesViewModelFactory @Inject constructor(
    private val cryptocurrenciesViewModel: CryptocurrenciesViewModel) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CryptocurrenciesViewModel::class.java)) {
      return cryptocurrenciesViewModel as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}