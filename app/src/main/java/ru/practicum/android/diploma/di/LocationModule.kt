package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.datasourse.LocationStorage
import ru.practicum.android.diploma.data.datasourse.LocationStorageImpl
import ru.practicum.android.diploma.data.mapper.LocationMapper
import ru.practicum.android.diploma.data.repository.LocationRepositoryImpl
import ru.practicum.android.diploma.domain.api.LocationRepository
import ru.practicum.android.diploma.domain.usecase.filters.location.ClearLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.GetLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase
import ru.practicum.android.diploma.ui.location.LocationViewModel

val locationModule = module {

    factory {
        LocationMapper(
            countryMapper = get(),
            regionMapper = get()
        )
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            mapper = get(),
            storage = get()
        )
    }

    single<LocationStorage> {
        LocationStorageImpl(
            gson = get(),
            sharedPreferences = get()
        )
    }

    single {
        ClearLocationUseCase(get())
    }

    single {
        GetLocationUseCase(get())
    }

    single {
        SetLocationUseCase(get())
    }

    viewModel {
        LocationViewModel(
            getLocationUseCase = get(),
            clearLocationUseCase = get(),
            setLocationUseCase = get(),
            getTmpFiltersUseCase = get(),
            setTmpFilterUseCase = get()
        )
    }
}
