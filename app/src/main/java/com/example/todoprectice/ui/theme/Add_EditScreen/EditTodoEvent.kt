package com.example.todoprectice.ui.theme.Add_EditScreen

sealed class EditTodoEvent{
    data class OnTitleChange(var title:String):EditTodoEvent()
    data class OnDescriptionChange(var description:String):EditTodoEvent()
    object OnSaveClick:EditTodoEvent()
}
