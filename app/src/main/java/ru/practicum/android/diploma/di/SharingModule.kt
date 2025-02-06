package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.sharing.SharingInteract
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractImpl

val sharingModule = module {

    single<SharingInteract> {
        SharingInteractImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}
