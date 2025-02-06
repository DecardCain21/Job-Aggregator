package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.AuthorizationInterceptor
import ru.practicum.android.diploma.data.network.HeadHunterApiService

private const val HEAD_HUNTER_BASE_URL = "https://api.hh.ru"
private const val KEY_FILTERS = "filters"

val dataModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "database.db"
        ).fallbackToDestructiveMigration().build()

    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()
    }

    single<HeadHunterApiService> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(HEAD_HUNTER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(KEY_FILTERS, Context.MODE_PRIVATE)
    }

    factory {
        Gson()
    }
}
