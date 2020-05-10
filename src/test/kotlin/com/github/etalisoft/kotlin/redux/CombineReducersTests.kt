package com.github.etalisoft.kotlin.redux

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CombineReducersTests {
    private data class Person(val name: String = "", val age: Int = 0)
    private data class UpdatePerson(val name: String? = null, val age: Int? = null)
    private fun personReducer(state: Person, action: Any) = when (action) {
        is UpdatePerson -> Person(action.name ?: state.name, action.age ?: state.age)
        else -> state
    }

    private data class Text(val value: String = "", val length: Int = 0)
    private data class UpdateText(val value: String)
    private fun textReducer(state: Text, action: Any) = when (action) {
        is UpdateText -> Text(action.value, action.value.length)
        else -> state
    }

    @BeforeEach
    fun beforeEach() {
    }

    @Test
    fun `should combine multiple reducers into one`() = runBlocking {
        val store = createStore(
            combineReducers(
                "person" to ::personReducer,
                "text" to ::textReducer
            ),
            mapOf(
                "person" to Person(),
                "text" to Text()
            )
        )

        store.dispatch(UpdatePerson("Bob", 42))
        store.dispatch(UpdateText("Word"))

        assertThat(store.state).isEqualTo(
            mapOf(
                "person" to Person("Bob", 42),
                "text" to Text("Word", 4)
            )
        )
    }
}
