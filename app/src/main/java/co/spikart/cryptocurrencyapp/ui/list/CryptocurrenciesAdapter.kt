package co.spikart.cryptocurrencyapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.spikart.cryptocurrencyapp.R
import co.spikart.cryptocurrencyapp.data.Cryptocurrency
import co.spikart.cryptocurrencyapp.ui.list.CryptocurrenciesAdapter.CryptocurrencieViewHolder
import java.util.ArrayList


class CryptocurrenciesAdapter(
    cryptocurrencies: List<Cryptocurrency>?) : androidx.recyclerview.widget.RecyclerView.Adapter<CryptocurrencieViewHolder>() {

  private var cryptocurrenciesList = ArrayList<Cryptocurrency>()

  init {
    this.cryptocurrenciesList = cryptocurrencies as ArrayList<Cryptocurrency>
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencieViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.cryptocurrency_list_item,
        parent, false)
    return CryptocurrencieViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return cryptocurrenciesList.size
  }

  override fun onBindViewHolder(holder: CryptocurrencieViewHolder, position: Int) {
    val cryptocurrencyItem = cryptocurrenciesList[position]
    holder?.cryptocurrencyListItem(cryptocurrencyItem)
  }

  fun addCryptocurrencies(cryptocurrencies: List<Cryptocurrency>){
    val initPosition = cryptocurrenciesList.size
    cryptocurrenciesList.addAll(cryptocurrencies)
    notifyItemRangeInserted(initPosition, cryptocurrenciesList.size)
  }

  class CryptocurrencieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    var cryptocurrencyId = itemView.findViewById<TextView>(R.id.cryptocurrency_id)

    fun cryptocurrencyListItem(cryptocurrencyItem: Cryptocurrency) {
      cryptocurrencyId.text = cryptocurrencyItem.id
    }
  }
}
