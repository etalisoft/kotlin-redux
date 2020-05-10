package com.github.etalisoft.kotlin.redux

data class Event<T>(val state: T, val action: Any)
