package com.sdv.main_feature.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.sdv.main_feature.R
import com.sdv.main_feature.presentation.MainContract.Action
import com.sdv.main_feature.presentation.MainContract.State


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun MainScreen(
    state: State,
    onAction: (Action) -> Unit,
    padding: PaddingValues,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.logFile) {
        state.logFile?.let { file ->
            val uri = FileProvider.getUriForFile(context, "UploadLogs", file)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_STREAM, uri)
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            if (state.sendByMessenger) { //messenger
                intent.type = "*/*"
            } else { //email
                val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
                emailSelectorIntent.setData(Uri.parse("mailto:"))
                intent.selector = emailSelectorIntent
            }

            onAction.invoke(Action.MakeLogFileNull)
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.parent),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Row {
                    IconButton(
                        modifier = Modifier
                            .padding(end = 48.dp)
                            .size(24.dp),
                        onClick = {
                            onAction.invoke(Action.OnClickShareByMessenger)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(R.string.logs_by_messenger),
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .size(24.dp),
                        onClick = {
                            onAction.invoke(Action.OnClickShareByEmail)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = stringResource(R.string.logs_by_email),
                            tint = Color.Black
                        )
                    }
                }


            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable {
                        if (state.currentParent?.id == 1L) {
                            showToast(context.getString(R.string.root_element), context)
                            return@clickable
                        }
                        onAction.invoke(Action.OnClickGoToParent)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(24.dp),
                        //    .align(Alignment.End),
                        onClick = {
                            if (state.currentParent?.id == 1L) {
                                showToast(context.getString(R.string.root_element), context)
                                return@IconButton
                            }
                            onAction.invoke(Action.OnClickDeleteParent(state.currentParent))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.delete_parent),
                            tint = Color.Red
                        )
                    }

                    Text(
                        fontSize = 14.sp,
                        text = stringResource(R.string.number, state.currentParent?.id ?: 0)
                    )
                    Text(
                        fontSize = 14.sp,
                        text = stringResource(R.string.name, state.currentParent?.name ?: "")
                    )
                    Text(
                        fontSize = 14.sp,
                        text = stringResource(
                            R.string.count_parent,
                            state.currentParent?.countParent ?: 0
                        )
                    )
                    Text(
                        fontSize = 14.sp,
                        text = stringResource(
                            R.string.count_children,
                            state.currentParent?.countChildren ?: 0
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Black)

            Text(
                text = stringResource(R.string.children),
                modifier = Modifier.padding(top = 16.dp)
            )

            LazyColumn(modifier = Modifier.padding(bottom = 12.dp)) {
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.End),
                                onClick = {
                                    onAction.invoke(Action.OnClickDeleteChildren(item))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.delete_child),
                                    tint = Color.Red
                                )
                            }
                            Text(fontSize = 12.sp, text = stringResource(R.string.number, item.id))
                            Text(fontSize = 12.sp, text = stringResource(R.string.name, item.name))
                            Text(
                                fontSize = 12.sp,
                                text = stringResource(R.string.count_parent, item.countParent)
                            )
                            Text(
                                fontSize = 12.sp,
                                text = stringResource(R.string.count_children, item.countChildren)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun showToast(text: String, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
