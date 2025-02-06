package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.mapper.VacancyDetailsMapper
import ru.practicum.android.diploma.data.network.client.DetailsNetworkClient
import ru.practicum.android.diploma.data.repository.DetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.DetailsRepository
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacancyDetailsUseCase
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val detailsModule = module {

    single {
        DetailsNetworkClient(get())
    }

    factory {
        VacancyDetailsMapper()
    }

    single {
        GetVacancyDetailsUseCase(get())
    }

    viewModel {
        VacancyViewModel(
            getVacancyDetailsUseCase = get(),
            getFavoriteVacancyByIdUseCase = get(),
            addVacancyToFavoriteUseCase = get(),
            deleteVacancyFromFavoriteUseCase = get(),
            sharingInteract = get()
        )
    }

    single<DetailsRepository> {
        DetailsRepositoryImpl(
            detailsNetworkClient = get(),
            vacancyDetailsMapper = get()
        )
    }
}
