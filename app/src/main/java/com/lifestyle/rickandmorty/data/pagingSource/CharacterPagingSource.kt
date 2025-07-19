package com.lifestyle.rickandmorty.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lifestyle.rickandmorty.data.mapper.CharacterDTOToCharacterMapper
import com.lifestyle.rickandmorty.data.mapper.mapAll
import com.lifestyle.rickandmorty.data.network.ApiService
import com.lifestyle.rickandmorty.domain.model.Character
import kotlinx.coroutines.delay

class CharacterPagingSource(
    private val apiService: ApiService,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val gender: String? = null,
    private val mapper: CharacterDTOToCharacterMapper
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            delay(500)
            val characters = apiService.getCharacters(page = page, name = name, species = species, status = status, gender = gender)
            LoadResult.Page(
                data = mapper.mapAll(characters.results),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (characters.results.size < pageSize) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}