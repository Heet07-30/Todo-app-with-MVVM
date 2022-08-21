package com.example.todoprectice.ui.theme.TodoListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoprectice.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    event: (TodoListEvent) -> Unit,
    modifier: Modifier
) {

    Row(modifier = modifier) {

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = todo.title, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    modifier = Modifier.clickable {
                        event(TodoListEvent.OnDeleteTodo(todo))
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            todo.description?.let { description ->
                Text(text = description, color = Color.White, fontWeight = FontWeight.Normal, maxLines = 3)
            }
        }
        Checkbox(checked = todo.isDone, onCheckedChange = {
            event(TodoListEvent.OnDoneChange(todo = todo, isDone = it))
        })
    }
}