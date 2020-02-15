package co.spikart.cryptocurrencyapp.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.migration.Migration
import co.spikart.cryptocurrencyapp.data.source.local.CryptocurrenciesDao
import co.spikart.cryptocurrencyapp.data.source.local.Database
import co.spikart.cryptocurrencyapp.ui.list.CryptocurrenciesViewModelFactory
import co.spikart.cryptocurrencyapp.utils.Constants
import co.spikart.cryptocurrencyapp.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: Application) {

  companion object {
    val MIGRATION_1_2: Migration = object : Migration(1, 2){
      override fun migrate(database: SupportSQLiteDatabase) {
        // Change the table name to the correct one
        database.execSQL("ALTER TABLE cryptocurrency RENAME TO cryptocurrencies")
      }
    }
  }

  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  @Singleton
  fun provideCryptocurrenciesDatabase(app: Application): Database = Room.databaseBuilder(app,
      Database::class.java, Constants.DATABASE_NAME)
      /*.addMigrations(MIGRATION_1_2)*/
      .fallbackToDestructiveMigration()
      .build()

  @Provides
  @Singleton
  fun provideCryptocurrenciesDao(
      database: Database): CryptocurrenciesDao = database.cryptocurrenciesDao()

  @Provides
  @Singleton
  fun provideCryptocurrenciesViewModelFactory(
      factory: CryptocurrenciesViewModelFactory): ViewModelProvider.Factory = factory

  @Provides
  @Singleton
  fun provideUtils(): Utils = Utils(app)
}