package com.sdv.main_feature.presentation

import androidx.lifecycle.viewModelScope
import com.sdv.base_feature.MviViewModel
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.domain.model.NodeUI
import com.sdv.main_feature.domain.usecase.AddNodeUseCase
import com.sdv.main_feature.domain.usecase.DeleteNodeUseCase
import com.sdv.main_feature.domain.usecase.GetAllNodesUseCase
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCase
import com.sdv.main_feature.domain.usecase.GetFileLogsUseCase
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCase
import com.sdv.main_feature.domain.usecase.GoToChildrenUseCase
import com.sdv.main_feature.domain.usecase.GoToParentUseCase
import com.sdv.main_feature.domain.usecase.SetFirstParentUseCase
import com.sdv.main_feature.presentation.MainContract.Action
import com.sdv.main_feature.presentation.MainContract.GRAND_PARENT
import com.sdv.main_feature.presentation.MainContract.PARENT_NODE
import com.sdv.main_feature.presentation.MainContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val dataStorage: DataStorage,
    private val setFirstParentUseCase: SetFirstParentUseCase,
    private val addNodeUseCase: AddNodeUseCase,
    private val getNodeByIdUseCase: GetNodeByIdUseCase,
    private val getChildrenForParentByIdUseCase: GetChildrenForParentByIdUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val goToParentUseCase: GoToParentUseCase,
    private val goToChildrenUseCase: GoToChildrenUseCase,
    private val getAllNodesUseCase: GetAllNodesUseCase,
    private val getFileLogsUseCase: GetFileLogsUseCase,
) : MviViewModel<State, Action>() {

    init {
        loadData()
        initFlowCollect()
    }

    override fun initialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.OnClickAddChild -> addChild()
            Action.OnClickGoToParent -> goToParent()
            is Action.OnClickGoToChildren -> goToChildren(action.nodeUI)
            is Action.OnClickDeleteChildren -> deleteNode(action.nodeUI, false)
            is Action.OnClickDeleteParent -> deleteNode(action.nodeUI, true)
            Action.MakeLogFileNull -> setState { it.copy(logFile = null) }
            Action.OnClickShareByEmail -> sendLogs(isMessenger = false)
            Action.OnClickShareByMessenger -> sendLogs(isMessenger = true)
            Action.MakeTextErrorNull -> setState { it.copy(textError = null) }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val currentParentId = dataStorage.currentParent.first()
            val currentParent = getNodeByIdUseCase(currentParentId)
            if (currentParent == null) setFirstParentUseCase()
        }
    }

    private fun initFlowCollect() {
        viewModelScope.launch {
            getAllNodesUseCase().collect { listAll ->
                val listChildren: MutableList<NodeUI> = mutableListOf()
                var currentParent: NodeUI? = null
                listAll.forEach { nodeUI ->
                    if (nodeUI.id == dataStorage.currentParent.first()) currentParent = nodeUI
                    if (nodeUI.idParent == dataStorage.currentParent.first()) listChildren.add(
                        nodeUI
                    )
                }
                setState {
                    it.copy(
                        currentParent = currentParent,
                        currentChildren = listChildren.toList()
                    )
                }
            }
        }
        viewModelScope.launch {
            dataStorage.currentParent.collect { currentParent ->
                val newParent = getNodeByIdUseCase(currentParent)
                val newChildren = getChildrenForParentByIdUseCase(currentParent)
                setState { it.copy(currentParent = newParent, currentChildren = newChildren) }
            }
        }
    }

    private fun addChild() {
        viewModelScope.launch {
            addNodeUseCase(currentState.currentParent)
        }
    }

    private fun goToParent() {
        viewModelScope.launch {
            if (currentState.currentParent?.id == PARENT_NODE) {
                setState { it.copy(textError = GRAND_PARENT) }
                return@launch
            }
            val newParentId = currentState.currentParent?.idParent ?: 0
            goToParentUseCase(newParentId)
        }
    }

    private fun goToChildren(nodeUI: NodeUI) {
        viewModelScope.launch {
            val newParentId = nodeUI.id
            goToChildrenUseCase(newParentId)
        }
    }

    private fun deleteNode(nodeUI: NodeUI?, fromParent: Boolean) {
        nodeUI?.let {
            viewModelScope.launch {
                if (currentState.currentParent?.id == PARENT_NODE && fromParent) {
                    setState { it.copy(textError = GRAND_PARENT) }
                    return@launch
                }
                val newParentId =
                    if (fromParent) nodeUI.idParent else currentState.currentParent?.id ?: 0
                deleteNodeUseCase(nodeUI, newParentId)
            }
        }
    }

    private fun sendLogs(isMessenger: Boolean) {
        viewModelScope.launch {
            val logFile = getFileLogsUseCase()
            setState { it.copy(logFile = logFile, sendByMessenger = isMessenger) }
        }
    }
}