package com.example.todoprectice.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoprectice.ui.theme.Add_EditScreen.EditTodoEvent
import com.example.todoprectice.ui.theme.Add_EditScreen.TodoViewModel
import com.example.todoprectice.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun Add_EditTodoScreen(
    onPopBack: () -> Unit,
    viewmodel: TodoViewModel = hiltViewModel()
) {
    val todo = viewmodel.todo
    var scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {

        viewmodel.uiEvent.collect {

            when (it) {

                is UiEvent.PopBackStack -> {
                    onPopBack()
                }

                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewmodel.onEvent(EditTodoEvent.OnSaveClick)
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "save")
            }
        }) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {

            TextField(value = viewmodel.title, onValueChange = {
                viewmodel.onEvent(EditTodoEvent.OnTitleChange(it))
            }, placeholder = {
                Text(text = "Title")
            }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = viewmodel.description, onValueChange = {
                viewmodel.onEvent(EditTodoEvent.OnDescriptionChange(it))
            }, placeholder = {
                Text(text = "Description")
            }, modifier = Modifier.fillMaxWidth())
        }
    }
}