package com.lifestyle.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lifestyle.rickandmorty.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val useCase: GetCharactersUseCase) :
    ViewModel() {
    private val _filterState = MutableStateFlow(CharacterFilterState())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val characters = _filterState
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            useCase.invoke(
                name = filter.name,
                gender = filter.gender,
                species = filter.species,
                status = filter.status
            ).flow.cachedIn(viewModelScope)
        }

    fun updateFilters(newFilter: CharacterFilterState) {
        _filterState.update { newFilter }
    }
}