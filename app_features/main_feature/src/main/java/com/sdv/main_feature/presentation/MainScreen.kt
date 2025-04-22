package com.sdv.main_feature.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdv.main_feature.presentation.MainContract.Action
import com.sdv.main_feature.presentation.MainContract.State
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = { onAction.invoke(Action.OnClickAddChild) },
                containerColor = Color.Yellow
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        contentColor = Color.Black,
        containerColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = padding.calculateTopPadding(), horizontal = 8.dp)
        ) {
            Text(
                text = "Родитель:",
                modifier = Modifier.padding(top = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable {
                        onAction.invoke(Action.OnClickGoToParent)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.End),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Удалить родителя",
                            tint = Color.Red)
                    }
                    Text(fontSize = 12.sp, text="Номер: ${state.currentParent?.id ?: ""}")
                    Text(fontSize = 12.sp, text="Имя: ${state.currentParent?.name ?: ""}")
                    Text(fontSize = 12.sp, text="Количество родителей: ${state.currentParent?.countParent ?: ""}")
                    Text(fontSize = 12.sp, text="Количество детей: ${state.currentParent?.countChildren ?: ""}")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Black)

            Text(
                text = "Дети:",
                modifier = Modifier.padding(top = 16.dp)
            )

            LazyColumn {
                itemsIndexed(state.currentChildren) { _, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clickable {
                                onAction.invoke(Action.OnClickGoToChildren(item))
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)) {
                            IconButton(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.End),
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Удалить ребенка",
                                    tint = Color.Red)
                            }
                            Text(fontSize = 10.sp, text="Номер: ${item.id}")
                            Text(fontSize = 10.sp, text="Имя: ${item.name}")
                            Text(fontSize = 10.sp, text="Количество родителей: ${item.countParent}")
                            Text(fontSize = 10.sp, text="Количество детей: ${item.countChildren}")
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
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
