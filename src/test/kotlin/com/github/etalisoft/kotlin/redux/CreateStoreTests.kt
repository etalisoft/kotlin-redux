package com.github.etalisoft.kotlin.redux

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateStoreTests {
    private data class State(val x: Int = 0, val y: Int = 0)
    private data class Increment(val x: Int? = null, val y: Int? = null)
    private fun reducer(state: State, action: Any) = when(action) {
        is Increment -> State(state.x + (action.x ?: 0), state.y + (action.y ?: 0))
        else -> state
    }

    private lateinit var store: Store<State>

    @BeforeEach
    fun beforeEach() {
        store = createStore(::reducer, State())
    }

    @Test
    fun `should dispatch actions`() = runBlocking {
        val jobs = mutableListOf<Job>()
        // Spin up 5 coroutines that will each dispatch 2 actions (5 times)
        repeat(5) {
            CoroutineScope(Dispatchers.Default).launch {
                repeat(5) {
                    store.dispatch(Increment(x = 1))
                    store.dispatch(Increment(y = 1))
                }
            }.let { jobs.add(it) }
        }
        jobs.joinAll()
        // We should see that the state is accurately updated after the coroutines are done
        assertThat(store.state).isEqualTo(State(25, 25))
    }

    @Test
    fun `should have suspend listeners`() = runBlocking {
        var x: Int? = null
        val done = Job()
        val unsub = store.subscribe {
            delay(1)
            x = store.state.x
            done.complete()
        }
        store.dispatch(Increment(1, 0))

        // Action has already been dispatched and is visible in `state`, but `x` hasn't been set by the subscription due to the delay
        assertThat(store.state).isEqualTo(State(1, 0))
        assertThat(x).isNull()

        // Wait for the subscription delay to finish and then unsubscribe
        done.join().also { unsub() }

        // `x` should be updated now
        assertThat(x).isEqualTo(1)
        assertThat(store.state).isEqualTo(State(1, 0))
    }

    @Test
    fun `should be observable (channel)`() = runBlocking {
        val xs = mutableListOf<Int>()
        val sub1 = store.channel
        CoroutineScope(Dispatchers.Default).launch {
            sub1.consumeEach { xs.add(it.state.x) }
        }

        store.dispatch(Increment(1, 0))


        val ys = mutableListOf<Int>()
        val sub2 = store.channel
        CoroutineScope(Dispatchers.Default).launch {
            sub2.consumeEach { ys.add(it.state.y) }
        }
        delay(1) // NOTE: Need to delay in order to give sub2's coroutine a chance to run

        store.dispatch(Increment(0, 1))
        store.dispatch(Increment(1, 1))

        assertThat(xs).isEqualTo(listOf(1, 1, 2))
        assertThat(ys).isEqualTo(listOf(1, 2))

        sub1.cancel()
        sub2.cancel()
    }

    @Test
    fun `should be observable (flow)`() = runBlocking {
        val x10 = CoroutineScope(Dispatchers.Default).async {
            store.flow
                .first { (it.action as? Increment)?.x == 10 }
        }

        repeat(20) {
            store.dispatch(Increment(it))
            delay(1)
        }

        assertThat(store.state).isEqualTo(State(190, 0))
        assertThat(x10.await()).isEqualTo(Event(State(55, 0), Increment(10, null)))
    }
}
