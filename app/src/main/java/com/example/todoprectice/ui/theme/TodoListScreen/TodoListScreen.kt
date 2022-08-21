package com.example.todoprectice.ui.theme.TodoListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoprectice.data.Todo
import com.example.todoprectice.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collect {

            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoClick)
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState,floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(TodoListEvent.OnAddTodo)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
        }
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(todos.value) { todo: Todo ->
                TodoItem(todo = todo, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(TodoListEvent.OnTodoSelected(todo))
                    }, event = viewModel::onEvent
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}