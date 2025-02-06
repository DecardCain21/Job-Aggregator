package ru.practicum.android.diploma.app

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.countryModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.detailsModule
import ru.practicum.android.diploma.di.favoriteModule
import ru.practicum.android.diploma.di.filterModule
import ru.practicum.android.diploma.di.industryModule
import ru.practicum.android.diploma.di.locationModule
import ru.practicum.android.diploma.di.regionModule
import ru.practicum.android.diploma.di.searchModule
import ru.practicum.android.diploma.di.sharingModule

class KoinProvider {
    init {
        startKoin {
            androidContext(NavigationJobApp.applicationContext())
            modules(
                countryModule,
                dataModule,
                detailsModule,
                favoriteModule,
                filterModule,
                industryModule,
                locationModule,
                regionModule,
                searchModule,
                sharingModule
            )
        }
    }
}
