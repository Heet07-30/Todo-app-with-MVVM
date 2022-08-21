package com.example.todoprectice.ui.theme.TodoListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoprectice.data.Repository
import com.example.todoprectice.data.Todo
import com.example.todoprectice.util.Routs
import com.example.todoprectice.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val todos = repository.getTodos()
    private var deletedTodo: Todo? = null
    private var _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent) {

        when (event) {

            is TodoListEvent.OnAddTodo -> {
                sendUiEvent(UiEvent.Navigate(Routs.Add_EditTodo.rout))
            }
            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    sendUiEvent(UiEvent.ShowSnackBar("Todo Deleted", "Undo"))
                    repository.deleteTodo(event.todo)
                }
            }
            is TodoListEvent.OnTodoSelected -> {
                sendUiEvent(UiEvent.Navigate(Routs.Add_EditTodo.rout + "?id=${event.todo.id}"))
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    var todo: Todo = event.todo
                    repository.insertTodo(todo.copy(isDone = event.isDone))
                }
            }
            is TodoListEvent.OnUndoClick -> {
                viewModelScope.launch {
                    deletedTodo?.let { todo ->
                        repository.insertTodo(todo)
                    }
                }
            }
        }
    }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}