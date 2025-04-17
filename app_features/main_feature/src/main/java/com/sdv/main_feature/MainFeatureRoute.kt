package com.sdv.main_feature

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sdv.main_feature.presentation.MainScreen
import com.sdv.main_feature.presentation.MainViewModel

@Composable
fun MainFeatureRoute(
    padding: PaddingValues
) {

    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    MainScreen(
        state = state.value,
        onAction = { viewModel.handleEvent(it) },
        padding = padding,
    )
}