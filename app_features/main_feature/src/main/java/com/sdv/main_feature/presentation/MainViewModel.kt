package com.sdv.main_feature.presentation

import androidx.lifecycle.viewModelScope
import com.sdv.base_feature.MviViewModel
import com.sdv.datastore.DataStorage
import com.sdv.main_feature.domain.model.NodeUI
import com.sdv.main_feature.domain.usecase.GetNodeByIdUseCase
import com.sdv.main_feature.domain.usecase.AddNodeUseCase
import com.sdv.main_feature.domain.usecase.GetChildrenForParentByIdUseCase
import com.sdv.main_feature.presentation.MainContract.Action
import com.sdv.main_feature.presentation.MainContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val dataStorage: DataStorage,
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
            Action.OnClickAddChild -> {}
            is Action.OnClickGoToParent -> {}
            is Action.OnClickGoToChildren -> TODO()
            is Action.OnClickDeleteChildren -> TODO()
            is Action.OnClickDeleteParent -> TODO()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val currentParentId = dataStorage.currentParent.first()
            var currentParent = getNodeByIdUseCase(currentParentId)
            if(currentParent==null) {
                addNodeUseCase(NodeUI())
                currentParent = getNodeByIdUseCase(currentParentId)
            }
          val currentChildren = getChildrenForParentByIdUseCase(currentParentId)
            setState { it.copy(currentParent = currentParent, currentChildren = currentChildren) }
        }
    }
}