package com.lifestyle.rickandmorty.data.mapper

import com.lifestyle.rickandmorty.data.model.remote.CharacterDTO
import com.lifestyle.rickandmorty.domain.model.Character
import javax.inject.Inject

class CharacterDTOToCharacterMapper @Inject constructor() : Mapper<CharacterDTO, Character> {
    override fun map(from: CharacterDTO): Character {
        return Character(
            id = from.id.toString(),
            name = from.name,
            imageUrl = from.image,
            gender = from.gender,
            status = from.status,
            species = from.species
        )
    }
}