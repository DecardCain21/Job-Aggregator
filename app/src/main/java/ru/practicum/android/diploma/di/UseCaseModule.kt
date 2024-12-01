package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.sharing.SharingInteract
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractImpl
import ru.practicum.android.diploma.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacanciesUseCase
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacancyDetailsUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.AddVacancyToFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.DeleteVacancyFromFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.GetAllFavoriteVacanciesUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.domain.usecase.filters.ClearFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.GetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.SetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.SetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.ClearLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.GetLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase

val useCaseModule = module {

    single {
        GetVacanciesUseCase(get())
    }

    single {
        GetVacancyDetailsUseCase(get())
    }

    single {
        AddVacancyToFavoriteUseCase(get())
    }

    single {
        DeleteVacancyFromFavoriteUseCase(get())
    }

    single {
        GetAllFavoriteVacanciesUseCase(get())
    }

    single {
        GetFavoriteVacancyByIdUseCase(get())
    }

    single<SharingInteract> {
        SharingInteractImpl(get())
    }

    single {
        SetTmpFiltersUseCase(get())
    }

    single {
        ClearFiltersUseCase(get())
    }

    single {
        GetTmpFiltersUseCase(get())
    }

    single {
        GetIndustriesUseCase(get())
    }

    single {
        GetCountriesUseCase(get())
    }

    single {
        SetSearchFiltersUseCase(get())
    }

    single {
        GetSearchFiltersUseCase(get())
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
}
