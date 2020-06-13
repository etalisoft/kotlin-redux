# Kotlin-Redux

> A [Redux](https://github.com/reduxjs/redux) implementation for Kotlin

## Example
```kotlin
// State
data class Person(val name: Name = Name(), val age: Int = 0)
data class Name(val first: String = "", val last: String = "")

// Actions
data class ChangeName(val first: String? = null, val last: String? = null)
data class ChangeAge(val age: Int)

// Reducer
fun reducer(state: Person, action: Any) = when(action) {
    is ChangeName -> Person(
        Name(
            action.first ?: state.name.first,
            action.last ?: state.name.last
        ),
        state.age
    )
    is ChangeAge -> Person(state.name, action.age)
    else -> state
}

// Store
val store = createStore(::reducer, Person())

store.dispatch(ChangeName("Bob", "Smith"))
store.dispatch(ChangeAge(42))

println(store.state) // Person(Name(first=Bob, last=Smith), age=42)
```

## `Store<T>`
The store makes heavy use of coroutine suspend functions.
The store handles concurrency issues internally ensuring critical interactions are mutually exclusive.
```kotlin
interface Store<T> {
    var dispatch: suspend (Any) -> Any
    val getState: suspend () -> T
    val state: T
    val replaceReducer: suspend (Reducer<T>) -> Store<T>
    val subscribe: suspend (Listener) -> Unsubscribe

    // Kotlin-specific implementation of observable
    val channel: ReceiveChannel<Event<T>>
    val flow: Flow<Event<T>>
}
```

## `applyMiddleware`
Redux supplies a utility called `applyMiddleware()` which will inject custom behavior into the pipeline. 
```kotlin
val loggingMiddleware: Middleware<MyAppState> =
    { store ->
        { next ->
            { action -> 
                println(action)
                next(action)
            }
        }
    }

val store = createStore(
    reducer, 
    MyAppState(),
    applyMiddleware(loggingMiddleware)
)
```
Need to dispatch actions asynchronously?
The standard way to do it with Redux is to use the 
[Redux Thunk middleware](https://github.com/etalisoft/kotlin-redux-thunk).

## `combineReducers`
Redux provides a utility called `combineReducers()`, which generates a reducer
that will call your reducers **with the slices of state selected according to their keys**,
and combines their result into a single `Map<String,Any?>`.  And like other reducers, 
`combineReducers()` does not create a new object if all of the reducers provided to it
do not change state.
```kotlin
data class Name(val first: String = "", val last: String = "")
fun personReducer(state: Person, action: Any) = when (action) {
    is ChangeName -> Name(
        action.first ?: state.first, 
        action.last ?: state.last
    )
    else -> state  
}

fun ageReducer(state: Int, action: Any) = when (action) {
    is ChangeAge -> action.age
    else -> state
}

val store = createStore(
    combineReducers(
        "name" to nameReducer,
        "age" to ageReducer
    ),
    mapOf(
        "name" to Name(),
        "age" to 0
    )
)

store.dispatch(ChangeName("Bob", "Smith"))
store.dispatch(ChangeAge(42))

println(store.state["name"]) // Name(first="Bob", last="Smith")
println(store.state["age"]) // 42
```

## Observable
Redux provides observability utilizing [Channel](https://kotlinlang.org/docs/reference/coroutines/channels.html) 
and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html).
Dispatching an action sends an `Event` containing the action and the resulting state.
 
### `store.channel`
The `channel` property subscribes to the internal `BroadcastChannel`. You are responsible
for calling `cancel()` on the subscription.
```kotlin
val subscription = store.channel
CoroutineScope(Dispatchers.Default).launch {
    subscription.consumeEach { 
        (state, action) -> println("$action $state")
    }
}
// subscription.cancel()
```

### `store.flow`
The `flow` property will return a hot `Flow<Event>` for the internal `BroadcastChannel`.
Flows are coroutine cooperative, which means the flow will be terminated when the 
coroutine exits. 
```kotlin
val bob = CoroutineScope(Dispatchers.Default).async {
    store.flow
        .first { (it.action as? ChangeName)?.name == "Bob" }
    }

store.dispatch(ChangeName("Bob", "Smith"))

println(bob.await())
```
