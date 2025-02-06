package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.datasourse.FilterStorage
import ru.practicum.android.diploma.data.datasourse.FilterStorageImpl
import ru.practicum.android.diploma.data.mapper.FilterMapper
import ru.practicum.android.diploma.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.usecase.filters.search.ClearSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.search.GetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.search.SetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.ClearTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.SetTmpFiltersUseCase
import ru.practicum.android.diploma.ui.filters.FiltersViewModel

val filterModule = module {

    viewModel {
        FiltersViewModel(
            setTmpFiltersUseCase = get(),
            getTmpFiltersUseCase = get(),
            clearSearchFiltersUseCase = get(),
            clearTmpFiltersUseCase = get(),
            getSearchFiltersUseCase = get(),
            setSearchFiltersUseCase = get()
        )
    }

    single<FilterStorage> {
        FilterStorageImpl(
            sharedPreferences = get(),
            gson = get()
        )
    }

    single<FilterRepository> {
        FilterRepositoryImpl(
            filterStorage = get(),
            mapper = get()
        )
    }

    factory {
        FilterMapper(
            industryMapper = get(),
            locationMapper = get()
        )
    }

    // Temporary filter

    single {
        SetTmpFiltersUseCase(get())
    }

    single {
        ClearTmpFiltersUseCase(get())
    }

    single {
        GetTmpFiltersUseCase(get())
    }

    // Search filter

    single {
        ClearSearchFiltersUseCase(get())
    }

    single {
        SetSearchFiltersUseCase(get())
    }

    single {
        GetSearchFiltersUseCase(get())
    }
}
