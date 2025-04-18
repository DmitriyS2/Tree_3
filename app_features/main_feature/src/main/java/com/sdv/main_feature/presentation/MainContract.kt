package com.sdv.main_feature.presentation

import com.sdv.base_feature.MviAction
import com.sdv.base_feature.MviState
import com.sdv.domain.model.Node

internal object MainContract {
    sealed interface Action : MviAction {
        data object OnClickAddChild: Action
        data class  OnClickGoToParent(val node: Node): Action
        data class  OnClickGoToChildren(val node: Node): Action
        data class OnClickDeleteParent(val node: Node): Action
        data class OnClickDeleteChildren(val node: Node): Action
    }
    data class State(
        val currentParent: Node = Node(),
    ) : MviState
}