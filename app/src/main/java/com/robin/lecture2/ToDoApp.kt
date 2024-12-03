package com.robin.lecture2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class NoteItem(
    val id: Int,
    var title: String,
    var subtitle: String,
    val check: MutableState<Boolean> = mutableStateOf(false)
)


@Composable
fun NoteApp() {
    val navController = rememberNavController()
    val noteList = remember { mutableStateListOf<NoteItem>() }

    NavHost(navController = navController, startDestination = "noteList") {
        composable("noteList") { NoteListScreen(navController, noteList) }
        composable("addNote") { AddNoteScreen(navController, noteList) }
        composable("editNote/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull()
            val noteItem = noteList.find { it.id == itemId }
            noteItem?.let { EditNoteScreen(navController, it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController, noteList: MutableList<NoteItem>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Note List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addNote") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(noteList) { item ->
                ListItem(
                    leadingContent = {
                        Checkbox(
                            checked = item.check.value,
                            onCheckedChange = {
                                item.check.value = !item.check.value
                        })},
                    headlineContent = { Text(item.title) },
                    supportingContent = { Text(item.subtitle)},
                    trailingContent = {
                        Row {
                            IconButton(
                                onClick = { navController.navigate("editNote/${item.id}") }
                            ) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Note")
                            }
                            IconButton(
                                onClick = { noteList.remove(item) }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Note")
                            }
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navController: NavController, todoList: MutableList<NoteItem>) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Todo") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank() && subtitle.isNotBlank()) {
                                todoList.add(NoteItem(id = todoList.size, title = title, subtitle = subtitle))
                                navController.popBackStack()
                            }
                        }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Todo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = subtitle,
                onValueChange = { subtitle = it },
                label = { Text("Details") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank() && subtitle.isNotBlank()) {
                    todoList.add(NoteItem(id = todoList.size, title = title, subtitle = subtitle))
                    navController.popBackStack()
                }
            }) {
                Text("Add Todo")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, noteItem: NoteItem) {
    var title by remember { mutableStateOf(noteItem.title) }
    var subtitle by remember { mutableStateOf(noteItem.subtitle) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Todo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Todo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = subtitle,
                onValueChange = { subtitle = it },
                label = { Text("Details") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank() && subtitle.isNotBlank()) {
                    noteItem.title = title
                    noteItem.subtitle = subtitle
                    navController.popBackStack()
                }
            }) {
                Text("Save Todo")
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun NotesAppPreview() {
    NoteApp()
}