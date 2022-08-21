package com.example.todoprectice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoprectice.ui.theme.Add_EditTodoScreen
import com.example.todoprectice.ui.theme.TodoListScreen.TodoListScreen
import com.example.todoprectice.ui.theme.TodoPrecticeTheme
import com.example.todoprectice.util.Routs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoPrecticeTheme {

                val navController= rememberNavController()

                NavHost(navController = navController, startDestination = Routs.TodoListScreen.rout){


                    composable(Routs.TodoListScreen.rout){
                        TodoListScreen(onNavigate = {
                            navController.navigate(it.rout)
                        })
                    }

                    composable(Routs.Add_EditTodo.rout+"?id={id}",
                        arguments = listOf(
                            navArgument("id"){
                                type= NavType.IntType
                                defaultValue=-1
                            }
                        )
                        ){
                        Add_EditTodoScreen(onPopBack = {
                            navController.popBackStack()
                        })
                    }

                }
            }
        }
    }
}