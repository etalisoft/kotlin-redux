package com.github.etalisoft.kotlin.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Creates a Redux store that holds the state tree.
 * The only way to change the data in the store is to call `dispatch()` on it.
 *
 * There should only be a single store in your app. To specify how different
 * parts of the state tree respond to actions, you may combine several reducers
 * into a single reducer function by using `combineReducers`.
 *
 * @param reducer A function that returns the next state tree, given
 * the current state tree and the action to handle.
 *
 * @param preloadedState The initial state.
 * If you use `combineReducers` to produce the root reducer function, this must be
 * an object with the same shape as `combineReducers` keys.
 *
 * @param enhancer The store enhancer. You may optionally specify it
 * to enhance the store with third-party capabilities such as middleware,
 * time travel, persistence, etc. The only store enhancer that ships with Redux
 * is `applyMiddleware()`.
 *
 * @return A Redux store that lets you read the state, dispatch actions
 * and subscribe to changes.
 */
fun <State> createStore(
    reducer: Reducer<State>,
    preloadedState: State,
    enhancer: StoreEnhancer<State>? = null
): Store<State> {
    val mutex = Mutex()
    val channel = BroadcastChannel<Event<State>>(1)
    lateinit var store: Store<State>

    if (enhancer != null) {
        return enhancer { r, s, _ -> createStore(r, s) }(reducer, preloadedState, null)
    }

    var currentReducer = reducer
    var currentState = preloadedState
    var currentListeners = emptyList<Listener>()
    var nextListeners = currentListeners

    /**
     * This makes a shallow copy of currentListeners so we can use
     * nextListeners as a temporary list while dispatching.
     *
     * This prevents any bugs around consumers calling
     * subscribe/unsubscribe in the middle of a dispatch.
     */
    fun ensureCanMutateNextListeners() {
        if (nextListeners === currentListeners) {
            nextListeners = currentListeners.toList()
        }
    }

    suspend fun getState(): State = mutex.withLock { currentState }

    suspend fun subscribe(listener: Listener): Unsubscribe = mutex.withLock {
        var isSubscribed = true

        ensureCanMutateNextListeners()
        nextListeners += listener

            return {
                mutex.withLock {
                    if (isSubscribed) {
                        isSubscribed = false

                        ensureCanMutateNextListeners()
                        nextListeners = nextListeners - listener
                        currentListeners = emptyList()
                    }
                }
            }
    }

    suspend fun dispatch(action: Any): Any {
        if (action is Function<*>) throw Exception("Actions must be plain objects. Use custom middleware for async actions.")
        mutex.withLock {
            currentState = currentReducer(currentState, action)
            channel.send(Event(currentState, action))
            currentListeners = nextListeners
        }

        CoroutineScope(Dispatchers.Default).launch {
            currentListeners.forEach { listener ->
                listener()
            }
        }

        return action
    }

    suspend fun replaceReducer(nextReducer: Reducer<State>): Store<State> = mutex.withLock {
        currentReducer = nextReducer
        dispatch(ActionTypes.REPLACE)
        return store
    }

    runBlocking {
        dispatch(ActionTypes.INIT)
    }

    store = object : Store<State> {
        override var dispatch: Dispatch = ::dispatch
        override val subscribe: Subscribe = ::subscribe
        override val getState: GetState<State> = ::getState
        override val replaceReducer: ReplaceReducer<State> = ::replaceReducer
        override val channel: ReceiveChannel<Event<State>>
            get() = channel.openSubscription()
        override val flow: Flow<Event<State>>
            get() = channel.asFlow()
    }

    return store
}
