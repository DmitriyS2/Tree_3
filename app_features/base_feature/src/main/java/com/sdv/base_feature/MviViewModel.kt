package com.sdv.base_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<S : MviState, A : MviAction> : ViewModel() {

    private val initialState: S by lazy { initialState() }

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action get() = _action.asSharedFlow()

    val currentState: S get() = state.value

    init {
        subscribeEvents()
    }

    abstract fun initialState(): S

    abstract fun handleEvent(action: A)

    fun setAction(action: A) {
        viewModelScope.launch { _action.emit(action) }
    }

    fun setState(reduce: (S) -> S) {
        _state.update { reduce(it) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            action.collect { handleEvent(it) }
        }
    }
}