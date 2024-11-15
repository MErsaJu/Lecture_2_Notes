package com.robin.lecture2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.robin.lecture2.ui.theme.Lecture2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lecture2Theme {
                // FloatingActionButtonExamples()
                // BottomNavigationBarExample()
                // CardExamples()
                // StateExample()
                // SmallTopAppBarExample()
                TodoApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Small Top App Bar")
                }
            )
        },
    ) { innerPadding ->
        // ScrollContent(innerPadding)
        Text("Hej hej")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lecture2Theme {
        // FloatingActionButtonExamples()
        // BottomNavigationBarExample()
        // StateExample()
        TodoApp()
    }
}