package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.mapper.CountryMapper
import ru.practicum.android.diploma.data.network.client.CountryNetworkClient
import ru.practicum.android.diploma.data.repository.CountryRepositoryImpl
import ru.practicum.android.diploma.domain.api.CountryRepository
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.ui.country.CountryViewModel

val countryModule = module {

    single<CountryRepository> {
        CountryRepositoryImpl(
            countryNetworkClient = get(),
            countryMapper = get()
        )
    }

    factory {
        CountryMapper(get())
    }

    single {
        CountryNetworkClient(get())
    }

    single {
        GetCountriesUseCase(get())
    }

    viewModel {
        CountryViewModel(
            getCountriesUseCase = get(),
            setLocationUseCase = get()
        )
    }
}
