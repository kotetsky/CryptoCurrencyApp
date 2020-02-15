package co.spikart.cryptocurrencyapp.ui.list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import co.spikart.cryptocurrencyapp.R
import co.spikart.cryptocurrencyapp.R.layout
import co.spikart.cryptocurrencyapp.data.Cryptocurrency
import co.spikart.cryptocurrencyapp.utils.Constants
import co.spikart.cryptocurrencyapp.utils.InfiniteScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cryptocurrencies.progressBar
import kotlinx.android.synthetic.main.activity_cryptocurrencies.recycler
import java.util.ArrayList
import javax.inject.Inject

class CryptocurrenciesActivity : AppCompatActivity() {

  @Inject
  lateinit var cryptocurrenciesViewModelFactory: CryptocurrenciesViewModelFactory
  var cryptocurrenciesAdapter = CryptocurrenciesAdapter(ArrayList())
  lateinit var cryptocurrenciesViewModel: CryptocurrenciesViewModel
  var currentPage = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_cryptocurrencies)
    AndroidInjection.inject(this)

    initializeRecycler()

    cryptocurrenciesViewModel = ViewModelProviders.of(this, cryptocurrenciesViewModelFactory).get(
        CryptocurrenciesViewModel::class.java)

    progressBar.visibility = View.VISIBLE
    loadData()

    cryptocurrenciesViewModel.cryptocurrenciesResult().observe(this,
        Observer<List<Cryptocurrency>> {
          if (it != null) {
            val position = cryptocurrenciesAdapter.itemCount
            cryptocurrenciesAdapter.addCryptocurrencies(it)
            recycler.adapter = cryptocurrenciesAdapter
            recycler.scrollToPosition(position - Constants.LIST_SCROLLING)
          }
        })

    cryptocurrenciesViewModel.cryptocurrenciesError().observe(this, Observer<String> {
      if (it != null) {
        Toast.makeText(this, resources.getString(R.string.cryptocurrency_error_message) + it,
            Toast.LENGTH_SHORT).show()
      }
    })

    cryptocurrenciesViewModel.cryptocurrenciesLoader().observe(this, Observer<Boolean> {
      if (it == false) progressBar.visibility = View.GONE
    })
  }

  private fun initializeRecycler() {
    val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1)
    gridLayoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
    recycler.apply {
      setHasFixedSize(true)
      layoutManager = gridLayoutManager
      addOnScrollListener(InfiniteScrollListener({ loadData() }, gridLayoutManager))
    }
  }

  fun loadData() {
    cryptocurrenciesViewModel.loadCryptocurrencies(Constants.LIMIT, currentPage * Constants.OFFSET)
    currentPage++
  }

  override fun onDestroy() {
    cryptocurrenciesViewModel.disposeElements()
    super.onDestroy()
  }
}
