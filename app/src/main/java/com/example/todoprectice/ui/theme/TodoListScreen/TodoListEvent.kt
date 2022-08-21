package com.example.todoprectice.ui.theme.TodoListScreen

import com.example.todoprectice.data.Todo

sealed class TodoListEvent{

    object OnAddTodo:TodoListEvent()
    data class OnDeleteTodo(val todo: Todo):TodoListEvent()
    data class OnTodoSelected(val todo: Todo):TodoListEvent()
    data class OnDoneChange(val todo:Todo,val isDone:Boolean):TodoListEvent()
    object OnUndoClick:TodoListEvent()

}
