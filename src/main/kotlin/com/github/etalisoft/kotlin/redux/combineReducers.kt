package com.github.etalisoft.kotlin.redux

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4, T5> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>,
    reducer5: Pair<String, Reducer<T5>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4, reducer5)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        reduce(reducer5, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4, T5, T6> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>,
    reducer5: Pair<String, Reducer<T5>>,
    reducer6: Pair<String, Reducer<T6>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4, reducer5, reducer6)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        reduce(reducer5, state, action, nextState)
        reduce(reducer6, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4, T5, T6, T7> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>,
    reducer5: Pair<String, Reducer<T5>>,
    reducer6: Pair<String, Reducer<T6>>,
    reducer7: Pair<String, Reducer<T7>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4, reducer5, reducer6, reducer7)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        reduce(reducer5, state, action, nextState)
        reduce(reducer6, state, action, nextState)
        reduce(reducer7, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>,
    reducer5: Pair<String, Reducer<T5>>,
    reducer6: Pair<String, Reducer<T6>>,
    reducer7: Pair<String, Reducer<T7>>,
    reducer8: Pair<String, Reducer<T8>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4, reducer5, reducer6, reducer7, reducer8)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        reduce(reducer5, state, action, nextState)
        reduce(reducer6, state, action, nextState)
        reduce(reducer7, state, action, nextState)
        reduce(reducer8, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

/**
 * Turns different reducer functions into a single reducer function. It will
 * call every child reducer, and gather their results into a single state
 * [Map], whose keys correspond to the keys of the passed reducer functions.
 *
 * @return A reducer function that invokes every reducer inside the passed
 *   [Map], and builds a state [Map] with the same shape.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> combineReducers(
    reducer1: Pair<String, Reducer<T1>>,
    reducer2: Pair<String, Reducer<T2>>,
    reducer3: Pair<String, Reducer<T3>>,
    reducer4: Pair<String, Reducer<T4>>,
    reducer5: Pair<String, Reducer<T5>>,
    reducer6: Pair<String, Reducer<T6>>,
    reducer7: Pair<String, Reducer<T7>>,
    reducer8: Pair<String, Reducer<T8>>,
    reducer9: Pair<String, Reducer<T9>>
)
    : Reducer<Map<String, Any?>> {
    requireUniqueKeys(reducer1, reducer2, reducer3, reducer4, reducer5, reducer6, reducer7, reducer8, reducer9)
    return { state, action ->
        val nextState = mutableMapOf<String, Any?>()
        reduce(reducer1, state, action, nextState)
        reduce(reducer2, state, action, nextState)
        reduce(reducer3, state, action, nextState)
        reduce(reducer4, state, action, nextState)
        reduce(reducer5, state, action, nextState)
        reduce(reducer6, state, action, nextState)
        reduce(reducer7, state, action, nextState)
        reduce(reducer8, state, action, nextState)
        reduce(reducer9, state, action, nextState)
        val hasChanged = state.any { it.value !== nextState[it.key] }
        if (hasChanged) nextState.toMap() else state
    }
}

private fun requireUniqueKeys(vararg pair: Pair<String, Reducer<*>>) {
    val keys = pair.map { it.first }.toSet()
    require(keys.size == pair.size) { "combineReducers requires all keys to be unique" }
}

private fun <T> reduce(
    reducer: Pair<String, Reducer<T>>,
    state: Map<String, Any?>,
    action: Any,
    nextState: MutableMap<String, Any?>
) {
    val (key, reduce) = reducer
    val current = state[key] as T
    nextState[key] = reduce(current, action)
}
