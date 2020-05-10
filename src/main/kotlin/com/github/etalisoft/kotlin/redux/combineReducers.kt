package com.github.etalisoft.kotlin.redux

/**
 * Turns multiple reducer functions into a single reducer function.
 *
 * @param T Combined state object type.
 * @param reducers Different reducer functions that need to be combined into one.
 * @return A reducer function that invokes every reducer.
 */
fun <T> combineReducers(vararg reducers: Reducer<T>): Reducer<T> =
    { state, action -> reducers.fold(state, { _state, reducer -> reducer(_state, action) }) }

operator fun <T> Reducer<T>.plus(other: Reducer<T>): Reducer<T> =
    { state, action -> other(this(state, action), action) }
