package com.github.etalisoft.kotlin.redux

/**
 * Creates a store enhancer that applies middleware to the dispatch method
 * of the Redux store. This is handy for a variety of tasks, such as expressing
 * asynchronous actions in a concise manner, or logging every action payload.
 *
 * See `redux-thunk` package as an example of the Redux middleware.
 *
 * Because middleware is potentially asynchronous, this should be the first
 * store enhancer in the composition chain.
 *
 * Note that each middleware will be given the `dispatch` and `getState` functions
 * as named arguments.
 *
 * @param T The type of the state supported by a middleware.
 * @param middlewares The middleware chain to be applied.
 * @return A store enhancer applying the middleware.
 */
fun <T> applyMiddleware(vararg middlewares: Middleware<T>): StoreEnhancer<T> {
    return { createStore: CreateStore<T> ->
        { reducer: Reducer<T>, initialState, enhancer ->
            val store = createStore(reducer, initialState, enhancer)
            val origDispatch = store.dispatch
            store.dispatch = { throw Exception("Dispatching while constructing your middleware is not allowed") }
            val chain = middlewares.map { middleware -> middleware(store) }
            store.dispatch = compose(chain)(origDispatch)
            store
        }
    }
}
