package com.sdv.main_feature

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sdv.main_feature.presentation.MainContract
import com.sdv.main_feature.presentation.MainScreen
import com.sdv.main_feature.presentation.MainViewModel

@Composable
fun MainFeatureRoute(padding: PaddingValues) {

    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    MainScreen(
        state = state.value,
        onAction = viewModel::handleEvent,
        padding = padding,
    )

    LaunchedEffect(Unit) {
        val toast: Toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainContract.Effect.ShowToast -> toast.apply {
                    setText(effect.text)
                    show()
                }
            }
        }
    }
}