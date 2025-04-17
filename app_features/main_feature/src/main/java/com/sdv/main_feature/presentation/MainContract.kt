package com.sdv.main_feature.presentation

import com.sdv.base_feature.MviAction
import com.sdv.base_feature.MviState

internal object MainContract {
    sealed interface Action : MviAction {

    }
    data class State(
        val isLoading: Boolean = false,
    ) : MviState
}