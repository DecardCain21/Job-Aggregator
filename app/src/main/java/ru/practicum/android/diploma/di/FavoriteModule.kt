package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.convertor.FavoriteVacancyDbConvertor
import ru.practicum.android.diploma.data.repository.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.usecase.favorite.AddVacancyToFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.DeleteVacancyFromFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.GetAllFavoriteVacanciesUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.ui.favorite.FavoriteViewModel

val favoriteModule = module {

    single<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(
            favoriteDao = get<AppDatabase>().favoriteVacanciesDao(),
            convertor = get()
        )
    }

    viewModel {
        FavoriteViewModel(
            getAllFavoriteVacanciesUseCase = get()
        )
    }

    factory {
        FavoriteVacancyDbConvertor()
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
}
