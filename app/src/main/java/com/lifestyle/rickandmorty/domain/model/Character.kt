package com.lifestyle.rickandmorty.domain.model

import java.util.UUID
import kotlin.uuid.Uuid

data class Character(
    val id: String,
    val name: String,
    val imageUrl: String,
    val species:String,
    val gender:String,
    val status:String,
    val uuid: String = UUID.randomUUID().toString()
)
