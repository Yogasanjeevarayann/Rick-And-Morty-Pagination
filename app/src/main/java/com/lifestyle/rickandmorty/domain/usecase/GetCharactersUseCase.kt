package com.lifestyle.rickandmorty.domain.usecase

import com.lifestyle.rickandmorty.domain.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(name: String? = null, status: String? = null, species: String? = null, gender: String? = null) = characterRepository.getCharacters(name, status, species, gender)
}