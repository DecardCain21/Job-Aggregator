package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.mapper.OptionMapper
import ru.practicum.android.diploma.data.mapper.VacancyMapper
import ru.practicum.android.diploma.data.network.client.VacancyNetworkClient
import ru.practicum.android.diploma.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacanciesUseCase
import ru.practicum.android.diploma.ui.search.SearchViewModel

val searchModule = module {

    factory {
        OptionMapper()
    }

    single {
        VacancyNetworkClient(get())
    }

    factory {
        VacancyMapper()
    }

    single {
        GetVacanciesUseCase(get())
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            vacancyNetworkClient = get(),
            vacancyMapper = get(),
            optionMapper = get(),
        )
    }

    viewModel {
        SearchViewModel(
            getVacanciesUseCase = get(),
            getTmpFiltersUseCase = get(),
            getSearchFiltersUseCase = get(),
            setSearchFiltersUseCase = get()
        )
    }
}
