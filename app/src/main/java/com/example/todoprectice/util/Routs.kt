package com.example.todoprectice.util

sealed class Routs(val rout:String){

    object TodoListScreen:Routs("TodoListScreen")
    object Add_EditTodo:Routs("Add_EditTodoScreen")

}
