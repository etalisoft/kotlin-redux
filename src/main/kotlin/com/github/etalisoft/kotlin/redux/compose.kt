package com.github.etalisoft.kotlin.redux

/**
 * Composes single-argument functions from right to left.
 *
 * @param funcs The functions to compose.
 * @return A function obtained by composing the argument functions from right
 *   to left. For example, `compose(f, g, h)` is identical to doing
 *   `{ f(g(h(it)) }`.
 */
fun <T> compose(vararg funcs: (T) -> T): (T) -> T = { funcs.foldRight(it, { f, x -> f(x) }) }

/**
 * Composes single-argument functions from right to left.
 *
 * @param funcs The functions to compose.
 * @return A function obtained by composing the argument functions from right
 *   to left. For example, `compose(listOf(f, g, h))` is identical to doing
 *   `{ f(g(h(it)) }`.
 */
fun <T> compose(funcs: List<(T) -> T>): (T) -> T = { funcs.foldRight(it, { f, x -> f(x) }) }
