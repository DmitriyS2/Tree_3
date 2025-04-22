package com.sdv.main_feature.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sdv.base_feature.MviViewModel
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.domain.model.NodeUI
import com.sdv.main_feature.domain.usecase.AddNodeUseCase
import com.sdv.main_feature.domain.usecase.DeleteNodeUseCase
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCase
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCase
import com.sdv.main_feature.domain.usecase.SetFirstParentUseCase
import com.sdv.main_feature.presentation.MainContract.Action
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
) : MviViewModel<State, Action>() {

    init {
        loadData()
    }

    override fun initialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.OnClickAddChild -> addChild()
            Action.OnClickGoToParent -> goToParent()
            is Action.OnClickGoToChildren -> goToChildren(action.nodeUI)
            is Action.OnClickDeleteChildren -> deleteNode(action.nodeUI, false)
            is Action.OnClickDeleteParent -> deleteNode(action.nodeUI, true)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val currentParentId = dataStorage.currentParent.first()
            Log.d("MyLog", "currentParentId=$currentParentId")
            var currentParent = getNodeByIdUseCase(currentParentId)
            Log.d("MyLog", "currentParent1=$currentParent")
            if (currentParent == null) {
                Log.d("MyLog", "currentParent=null if")
                setFirstParentUseCase()
                currentParent = getNodeByIdUseCase(currentParentId)
                Log.d("MyLog", "currentParent2=$currentParent")
            }
            val currentChildren = getChildrenForParentByIdUseCase(currentParentId)
            Log.d("MyLog", "currentChildren=$currentChildren")
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
        }
    }

    private fun addChild() {
        viewModelScope.launch {
            addNodeUseCase(state.value.currentParent)
            val currentChildren = getChildrenForParentByIdUseCase(state.value.currentParent?.id ?: 0)
            val currentParent = getNodeByIdUseCase(state.value.currentParent?.id ?: 0)
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
        }
    }

    private fun goToParent() {
        viewModelScope.launch {
            val newParentId = state.value.currentParent?.idParent ?: 0
            val currentParent = getNodeByIdUseCase(newParentId)
            val currentChildren = getChildrenForParentByIdUseCase(newParentId)
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
            dataStorage.setCurrentParent(newParentId)
        }
    }

    private fun goToChildren(nodeUI: NodeUI) {
        viewModelScope.launch {
            val newParentId = nodeUI.id
            val currentParent = getNodeByIdUseCase(newParentId)
            val currentChildren = getChildrenForParentByIdUseCase(newParentId)
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
            dataStorage.setCurrentParent(newParentId)
        }
    }

    private fun deleteNode(nodeUI: NodeUI?, fromParent:Boolean) {
        nodeUI?.let {
        viewModelScope.launch {
            deleteNodeUseCase(nodeUI)
            val newParentId = if (fromParent) nodeUI.idParent else state.value.currentParent?.id ?: 0
            val currentParent = getNodeByIdUseCase(newParentId)
            val currentChildren = getChildrenForParentByIdUseCase(newParentId)
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
            dataStorage.setCurrentParent(newParentId)
        }
    }
    }
}