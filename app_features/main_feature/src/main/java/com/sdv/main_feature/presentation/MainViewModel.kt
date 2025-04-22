package com.sdv.main_feature.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sdv.base_feature.MviViewModel
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.domain.model.NodeUI
import com.sdv.main_feature.domain.usecase.AddNodeUseCase
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
) : MviViewModel<State, Action>() {

    init {
        loadData()
    }

    override fun initialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.OnClickAddChild -> {addChild()}
            is Action.OnClickGoToParent -> {}
            is Action.OnClickGoToChildren -> TODO()
            is Action.OnClickDeleteChildren -> TODO()
            is Action.OnClickDeleteParent -> TODO()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val currentParentId = dataStorage.currentParent.first()
            Log.d("MyLog", "currentParentId=$currentParentId")
            var currentParent = getNodeByIdUseCase(currentParentId)
            Log.d("MyLog", "currentParent1=$currentParent")
            if(currentParent==null) {
                Log.d("MyLog", "currentParent=null if")
             //   addNodeUseCase(NodeUI())
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
}