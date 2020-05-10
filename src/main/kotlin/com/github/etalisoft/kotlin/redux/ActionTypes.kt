package com.github.etalisoft.kotlin.redux

object ActionTypes {
    /**
     * When a store is created, an [INIT] action is dispatched so that every
     * reducer returns their initial state. This effectively populates
     * the initial state tree.
     */
    object INIT
    /**
     * This action has a similar effect to [ActionTypes.INIT]
     * Any reducers that existed in both the new and old rootReducer
     * will receive the previous state. This effectively populates
     * the new state tree with any relevant data from the old one.
     */
    object REPLACE
}
