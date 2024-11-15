package com.robin.lecture2

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robin.lecture2.ui.theme.Lecture2Theme

@Composable
fun StateExample() {
    var textCheck by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(40.dp)) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                if (text == "hello") {
                    textCheck = true
                } else {
                    textCheck = false
                }
            },
            label = { Text("Enter password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        if (textCheck) {
            Text("Password is correct!", color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatePreview() {
    Lecture2Theme {
        StateExample()
    }
}