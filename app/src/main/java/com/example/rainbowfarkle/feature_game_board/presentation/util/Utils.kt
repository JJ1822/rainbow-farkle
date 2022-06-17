package com.example.rainbowfarkle.feature_game_board.presentation.util

inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(mutatorBlock)
}

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }