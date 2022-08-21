package com.example.todoprectice.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp(
    private val dao: TodoDao
) :Repository {

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }
}