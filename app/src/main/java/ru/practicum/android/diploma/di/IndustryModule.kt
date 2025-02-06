package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.mapper.IndustryMapper
import ru.practicum.android.diploma.data.network.client.IndustryNetworkClient
import ru.practicum.android.diploma.data.repository.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.ui.industry.IndustryViewModel

val industryModule = module {
    single {
        IndustryNetworkClient(get())
    }

    factory {
        IndustryMapper()
    }

    single<IndustryRepository> {
        IndustryRepositoryImpl(
            industryNetworkClient = get(),
            industryMapper = get()
        )
    }

    single {
        GetIndustriesUseCase(get())
    }

    viewModel {
        IndustryViewModel(
            getIndustriesUseCase = get(),
            getTmpFiltersUseCase = get(),
            setTmpFiltersUseCase = get()
        )
    }
}
