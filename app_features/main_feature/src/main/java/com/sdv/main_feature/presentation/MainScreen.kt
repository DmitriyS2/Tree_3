package com.sdv.main_feature.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdv.main_feature.presentation.MainContract.State
import com.sdv.main_feature.presentation.MainContract.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun MainScreen(
    state: State,
    onAction: (Action) -> Unit,
    padding: PaddingValues,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Добавить")
                },
                onClick = { //viewModel.addChild()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        contentColor = Color.Black,
        containerColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
              //  .background(Green100)
        ) {
            Column(
                modifier = Modifier.padding(top=padding.calculateTopPadding())
            ) {
                Text(
                //    color = Orange100,
                    text = "Родитель",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box {
//                    ShowCardItem(
//                        child = false,
//                        padding = 24,
//                        size = 13,
//                        currentChildOrParent = parentNow,
//                        expanded = expandedParent,
//                        context = this@MainActivity
//                    )

//                    ShowDropDownMenu(expanded = expandedParent,
//                        onChangeParent = {
//                            viewModel.changeCurrentParent(
//                                parentNow.value.idParent
//                            )
//                        },
//                        text = "Перейти к родителю",
//                        onDeleteParent = {
//                            viewModel.deleteParent()
//                        })
                }

                Box {
                    Text(
                   //     color = Orange100,
                        text = "Дети",
                        modifier = Modifier.padding(top = 32.dp)
                    )

//                    ShowDropDownMenu(expanded = expandedChild,
//                        onChangeParent = {
//                            viewModel.changeCurrentParent(currentChild.value.id)
//                        },
//                        text = "Перейти к детям",
//                        onDeleteParent = {
//                            viewModel.deleteParentFromChildren(currentChild.value)
//                        })
                }

//                ShowListItem(
//                    listChildren = listChildren,
//                    currentChild = currentChild,
//                    expandedChild = expandedChild
//                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    MainScreen(
        state = State(),
        onAction = {},
        padding = PaddingValues(0.dp)
    )
}
