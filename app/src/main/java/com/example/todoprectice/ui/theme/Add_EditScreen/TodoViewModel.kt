package com.example.todoprectice.ui.theme.Add_EditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoprectice.data.Repository
import com.example.todoprectice.data.Todo
import com.example.todoprectice.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    var todo:Todo? by mutableStateOf(null)
    private set

    var title by mutableStateOf("")
    private set

    var description by mutableStateOf("")
    private set

    init {
        viewModelScope.launch {
            val todoId=savedStateHandle.get<Int>("id")!!

            if (todoId!=-1){
                repository.getTodoById(todoId)?.let {
                    todo=it
                    title=it.title
                    description=it.description?:""
                }
            }
        }
    }

    private var _uiEvent= Channel<UiEvent>()
    var uiEvent=_uiEvent.receiveAsFlow()


    fun onEvent(event:EditTodoEvent){

        when (event){

            is EditTodoEvent.OnDescriptionChange->{
                description=event.description
            }
            is EditTodoEvent.OnTitleChange->{
                title=event.title
            }
            is EditTodoEvent.OnSaveClick->{
                 viewModelScope.launch {

                     if(title.isBlank()){
                         _uiEvent.send(UiEvent.ShowSnackBar("Please Enter title"))
                         return@launch
                     }
                     repository.insertTodo(
                         Todo(
                             title=title,
                             description=description,
                             isDone = false
                         )
                     )
                     _uiEvent.send(UiEvent.PopBackStack)
                 }
            }
        }
    }
}