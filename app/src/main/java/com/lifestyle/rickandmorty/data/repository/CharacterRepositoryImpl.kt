package com.lifestyle.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lifestyle.rickandmorty.data.mapper.CharacterDTOToCharacterMapper
import com.lifestyle.rickandmorty.data.network.ApiService
import com.lifestyle.rickandmorty.data.pagingSource.CharacterPagingSource
import com.lifestyle.rickandmorty.domain.CharacterRepository
import com.lifestyle.rickandmorty.domain.model.Character
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: CharacterDTOToCharacterMapper
) : CharacterRepository {
    override fun getCharacters(name: String?, status: String?, species: String?, gender: String?): Pager<Int, Character> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    apiService = apiService,
                    mapper = mapper,
                    name = name,
                    status = status,
                    gender = gender,
                    species = species
                )
            }
        )
    }
}