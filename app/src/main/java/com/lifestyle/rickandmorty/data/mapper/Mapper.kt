package com.lifestyle.rickandmorty.data.mapper

interface Mapper<F, T> {
    fun map(from: F): T
}

fun <F, T> Mapper<F, T>.mapAll(list: List<F>) = list.map { map(it) }