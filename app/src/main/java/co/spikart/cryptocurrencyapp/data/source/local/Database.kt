package co.spikart.cryptocurrencyapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.spikart.cryptocurrencyapp.data.Cryptocurrency

@Database(entities = arrayOf(Cryptocurrency::class), version = 8)
abstract class Database : RoomDatabase() {
  abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}