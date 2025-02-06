package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.mapper.RegionMapper
import ru.practicum.android.diploma.ui.region.RegionViewModel

val regionModule = module {
    factory {
        RegionMapper()
    }

    viewModel {
        RegionViewModel(
            setLocationUseCase = get(),
            getLocationUseCase = get(),
            getCountriesUseCase = get()
        )
    }
}
