package com.github.etalisoft.kotlin.redux

// NOTE: Enhancer is Any? to prevent circular dependency of types
typealias CreateStore<T> = (Reducer<T>, T, Any?) -> Store<T>
typealias Dispatch = suspend (Any) -> Any
typealias GetState<T> = suspend () -> T
typealias Listener = suspend () -> Unit
typealias Middleware<State> = (store: Store<State>) -> (next: Dispatch) -> suspend (action: Any) -> Any
typealias Reducer<T> = (T, Any) -> T
typealias ReplaceReducer<T> = suspend (Reducer<T>) -> Store<T>
typealias StoreEnhancer<T> = (CreateStore<T>) -> CreateStore<T>
typealias Subscribe = suspend (Listener) -> Unsubscribe
typealias Unsubscribe = suspend () -> Unit


