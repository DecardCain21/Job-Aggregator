package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.Source
import ru.practicum.android.diploma.common.Source.FAVORITE
import ru.practicum.android.diploma.common.Source.SEARCH
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.sharing.SharingInteract
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data.Empty
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data.Error
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data.Loading
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data.NoInternet
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Data.Payload
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Favorite.InFavorite
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsState.Favorite.NotInFavorite
import ru.practicum.android.diploma.domain.usecase.favorite.AddVacancyToFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.DeleteVacancyFromFavoriteUseCase
import ru.practicum.android.diploma.domain.usecase.favorite.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacancyDetailsUseCase

class VacancyViewModel(
    private val getVacancyDetailsUseCase: GetVacancyDetailsUseCase,
    private val getFavoriteVacancyByIdUseCase: GetFavoriteVacancyByIdUseCase,
    private val addVacancyToFavoriteUseCase: AddVacancyToFavoriteUseCase,
    private val deleteVacancyFromFavoriteUseCase: DeleteVacancyFromFavoriteUseCase,
    private val sharingInteract: SharingInteract
) : ViewModel() {

    private val _state = MutableLiveData<VacancyDetailsState>()
    val state: LiveData<VacancyDetailsState>
        get() = _state

    init {
        _state.postValue(VacancyDetailsState(Loading, NotInFavorite))
    }

    fun getVacancyDetails(id: String, source: Source) = viewModelScope.launch {
        val favoriteVacancy = getFavoriteVacancyByIdUseCase.execute(id)
        val favoriteState = if (favoriteVacancy == null) NotInFavorite else InFavorite

        val vacancyState = when (source) {
            SEARCH -> handleSearchSource(id)
            FAVORITE -> handleFavoriteSource(favoriteVacancy)
        }

        _state.postValue(VacancyDetailsState(vacancyState, favoriteState))
    }

    @Suppress("LabeledExpression")
    fun onFavoriteClicked(vacancyId: String) = viewModelScope.launch {
        when (val dataState = _state.value?.data) {
            is Payload -> {
                val dbFavoriteVacancy = getFavoriteVacancyByIdUseCase.execute(vacancyId)
                val favoriteState = if (dbFavoriteVacancy == null) {
                    addVacancyToFavoriteUseCase.execute(dataState.details)
                    InFavorite
                } else {
                    deleteVacancyFromFavoriteUseCase.execute(dataState.details)
                    NotInFavorite
                }

                _state.postValue(VacancyDetailsState(dataState, favoriteState))
            }

            else -> return@launch
        }
    }

    fun share() {
        val vacancy = _state.value?.data as? Payload ?: return
        sharingInteract.shareUrl(vacancy.details.url, vacancy.details.name)
    }

    private suspend fun handleSearchSource(vacancyId: String): Data {
        val result = getVacancyDetailsUseCase.execute(vacancyId)
        return when (result.exceptionOrNull()) {
            is NetworkError.BadCode, is NetworkError.ServerError -> Error
            is NetworkError.NoData -> Empty
            is NetworkError.NoInternet -> NoInternet
            else -> result.getOrNull()?.let { Payload(it) } ?: Error
        }
    }

    private fun handleFavoriteSource(
        favoriteVacancy: VacancyDetails?
    ): Data = when (favoriteVacancy) {
        null -> Error
        else -> Payload(favoriteVacancy)
    }
}
