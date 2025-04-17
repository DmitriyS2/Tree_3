package com.sdv.main_feature.presentation

import com.sdv.base_feature.MviViewModel
import com.sdv.main_feature.presentation.MainContract.Action
import com.sdv.main_feature.presentation.MainContract.State

internal class MainViewModel:MviViewModel<State, Action>() {

    override fun initialState() = State()

    override fun handleEvent(action: Action) {
        TODO("Not yet implemented")
    }
}