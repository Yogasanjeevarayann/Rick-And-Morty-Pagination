package com.lifestyle.rickandmorty.domain

import androidx.paging.Pager
import com.lifestyle.rickandmorty.domain.model.Character

interface CharacterRepository {
    fun getCharacters(name: String? = null, status: String? = null, species: String? = null, gender: String? = null): Pager<Int, Character>
}