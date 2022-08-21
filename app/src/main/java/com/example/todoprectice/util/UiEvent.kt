package com.example.todoprectice.util

sealed class UiEvent{

    object PopBackStack:UiEvent()
    data class Navigate(val rout:String):UiEvent()
    data class ShowSnackBar(val message:String,val action:String?=null):UiEvent()

}
