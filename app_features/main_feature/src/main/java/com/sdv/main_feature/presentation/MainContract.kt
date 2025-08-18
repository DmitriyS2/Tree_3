package com.sdv.main_feature.presentation

import com.sdv.base_feature.MviAction
import com.sdv.base_feature.MviEffect
import com.sdv.base_feature.MviState
import com.sdv.main_feature.domain.model.NodeUI
import java.io.File

internal object MainContract {
    sealed interface Action : MviAction {
        data object OnClickAddChild : Action
        data object OnClickGoToParent : Action
        data class OnClickGoToChildren(val nodeUI: NodeUI) : Action
        data class OnClickDeleteParent(val nodeUI: NodeUI?) : Action
        data class OnClickDeleteChildren(val nodeUI: NodeUI?) : Action
        data object OnClickShareByMessenger : Action
        data object OnClickShareByEmail : Action
    }

    data class State(
        val currentParent: NodeUI? = null,
        val currentChildren: List<NodeUI> = emptyList(),
    ) : MviState

    sealed interface Effect : MviEffect {
        data class ShowToast(val text: String) : Effect
        data class SendLogFile(val file: File, val sendingByMessenger: Boolean) : Effect
    }

    const val PARENT_NODE = 1L
    const val GRAND_PARENT = "Не получится. Это root-элемент"
}