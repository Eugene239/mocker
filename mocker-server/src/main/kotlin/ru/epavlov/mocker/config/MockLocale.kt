package ru.epavlov.mocker.config

import java.util.*
import kotlin.streams.toList

data class MockLocale(
        var available: List<String>? = emptyList(),
        var default: String? = null
) {
    fun getLocales(): MutableList<Locale> {
        return this.available!!.stream().map { Locale(it) }.toList().toMutableList()
    }

    fun getDefault(): Locale {
        return Locale(default)
    }
}