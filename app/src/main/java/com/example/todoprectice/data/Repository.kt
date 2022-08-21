package com.example.todoprectice.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

sealed interface Repository{


    fun getTodos(): Flow<List<Todo>>

    suspend fun getTodoById(id:Int):Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}