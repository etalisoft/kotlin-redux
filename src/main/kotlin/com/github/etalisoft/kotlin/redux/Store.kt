package com.github.etalisoft.kotlin.redux

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

interface Store<T> {
    /**
     * Dispatches an action. It is the only way to trigger a state change.
     *
     * The `reducer` function, used to create the store, will be called with the
     * current state tree and the given `action`. Its return value will
     * be considered the **next** state of the tree, and the change listeners
     * will be notified.
     *
     * The base implementation only supports plain object actions. If you want to
     * dispatch a thunk, or something else, you need to
     * wrap your store creating function into the corresponding middleware. For
     * example, see the documentation for the `redux-thunk` package. Even the
     * middleware will eventually dispatch plain object actions using this method.
     *
     * @param action A plain object representing “what changed”. It is a good idea
     * to keep actions serializable so you can record and replay user sessions.
     *
     * @return For convenience, the same action object you dispatched.
     *
     * Note that, if you use a custom middleware, it may wrap `dispatch()` to
     * return something else (for example, a Promise you can await).
     */
    var dispatch: Dispatch

    /**
     * Reads the state tree managed by the store.
     *
     * @return The current state tree of your application.
     */
    val getState: GetState<T>

    /**
     * Replaces the reducer currently used by the store to calculate the state.
     *
     * You might need this if your app implements code splitting and you want to
     * load some of the reducers dynamically. You might also need this if you
     * implement a hot reloading mechanism for Redux.
     *
     * @param nextReducer The reducer for the store to use instead.
     * @return The same store instance with a new reducer in place.
     */
    val replaceReducer: ReplaceReducer<T>

    /**
     * Adds a change listener. It will be called any time an action is dispatched,
     * and some part of the state tree may potentially have changed. You may then
     * call `getState()` to read the current state tree inside the callback.
     *
     * You may call `dispatch()` from a change listener, with the following
     * caveats:
     *
     * 1. The subscriptions are snapshotted just before every `dispatch()` call.
     * If you subscribe or unsubscribe while the listeners are being invoked, this
     * will not have any effect on the `dispatch()` that is currently in progress.
     * However, the next `dispatch()` call, whether nested or not, will use a more
     * recent snapshot of the subscription list.
     *
     * 2. The listener should not expect to see all state changes, as the state
     * might have been updated multiple times during a nested `dispatch()` before
     * the listener is called. It is, however, guaranteed that all subscribers
     * registered before the `dispatch()` started will be called with the latest
     * state by the time it exits.
     *
     * @param listener A callback to be invoked on every dispatch.
     * @returns A function to remove this change listener.
     */
    val subscribe: Subscribe

    val state: T
        get() = runBlocking { getState() }

    /**
     * Subscribes to this internal [BroadcastChannel] and returns a channel to receive [Event] from it.
     * The resulting channel shall be [cancelled][ReceiveChannel.cancel] to unsubscribe from this
     * broadcast channel.
     */
    val channel: ReceiveChannel<Event<T>>

    /**
     * Gets a new [Flow] which can be used to observe the redux [Event] stream.
     * Flows are coroutine cooperative, which means the flow will be terminated
     * when the coroutine exits.
     */
    val flow: Flow<Event<T>>
}
