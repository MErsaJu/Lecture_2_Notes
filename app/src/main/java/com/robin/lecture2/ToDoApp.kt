package com.robin.lecture2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    NavHost(navController = navController,
            startDestination = "noteList") {
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
fun NoteListScreen(navController: NavController,
                   noteList: MutableList<NoteItem>) {
    Scaffold(
        containerColor = Color(0xFFE3B7C9),
        topBar = {
            TopAppBar(
                title = { Text(
                text = "Note List",
                color = Color.White,
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color(0xFFA54A6F),
                            offset = Offset(4f, 8f),
                            blurRadius = 4f
                        )
                    ),
                fontStyle = FontStyle.Italic)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3B7C9))
                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addNote") },
                containerColor = Color(0xFFA54A6F),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(noteList) { item ->
                ListItem(
                    leadingContent = {
                        },
                    headlineContent = { Text(
                        item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFFA54A6F)
                    ) },
                    supportingContent = { Text(
                        item.subtitle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFFA54A6F)
                    )},
                    trailingContent = {
                        Row {
                            IconButton(
                                onClick = { navController.navigate("editNote/${item.id}") }
                            ) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Note", tint = Color(0xFFA54A6F))
                            }
                            IconButton(
                                onClick = { noteList.remove(item) }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Note", tint = Color(0xFFA54A6F))
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
fun AddNoteScreen(navController: NavController,
                  noteList: MutableList<NoteItem>) {
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var subtitle by remember { mutableStateOf("") }
    var subtitleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new Note",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color(0xFFA54A6F),
                            offset = Offset(4f, 8f),
                            blurRadius = 4f
                        )
                    ),
                    fontStyle = FontStyle.Italic) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()
                                && subtitle.isNotBlank()) {
                                noteList.add(NoteItem(
                                    id = noteList.size,
                                    title = title,
                                    subtitle = subtitle))
                                navController.popBackStack()
                            }
                        }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3B7C9)
                )
            )
        },
        containerColor = Color(0xFFE3B7C9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = title.length !in 3..50
                },
                label = { Text("Title", color = Color(0xFFA54A6F)) },
                isError = titleError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFA54A6F),
                    unfocusedTextColor = Color(0xFFA54A6F),
                    cursorColor = Color(0xFFA54A6F),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (titleError) {
                Text(
                    text = "Title must contain 3-50 characters",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = subtitle,
                onValueChange = {
                    subtitle = it
                    subtitleError = subtitle.length > 120
                },
                label = { Text("Details", color = Color(0xFFA54A6F)) },
                isError = subtitleError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFA54A6F),
                    unfocusedTextColor = Color(0xFFA54A6F),
                    cursorColor = Color(0xFFA54A6F),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (subtitleError) {
                Text(
                    text = "Details text must not exceed 120 characters",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank()
                    && subtitle.isNotBlank()
                    && title.length in 3..50
                    && subtitle.length <= 120) {
                    noteList.add(NoteItem(id = noteList.size, title = title, subtitle = subtitle))
                    navController.popBackStack()
                } else {
                    titleError = true
                    subtitleError = true
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA54A6F), // Same as FloatingActionButton color
                    contentColor = Color.White         // Text color
                ),
                modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 16.dp)
            ) {
                Text("Add Note")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, noteItem: NoteItem) {
    var title by remember { mutableStateOf(noteItem.title) }
    var titleError by remember { mutableStateOf(false) }
    var subtitle by remember { mutableStateOf(noteItem.subtitle) }
    var subtitleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color(0xFFA54A6F),
                            offset = Offset(4f, 8f),
                            blurRadius = 4f
                        )
                    ),
                    fontStyle = FontStyle.Italic) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.isNotBlank() && subtitle.isNotBlank() && title.length in 3..50 && subtitle.length <= 120) {
                            noteItem.title = title
                            noteItem.subtitle = subtitle
                            navController.popBackStack()
                        } else {
                            titleError = true
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3B7C9)
                )
            )
        },
        containerColor = Color(0xFFE3B7C9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = title.length !in 3..50
                },
                label = { Text("Note", color = Color(0xFFA54A6F)) },
                isError = titleError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFA54A6F),
                    unfocusedTextColor = Color(0xFFA54A6F),
                    cursorColor = Color(0xFFA54A6F),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (titleError) {
                Text(
                    text = "Title must contain 3-50 characters",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = subtitle,
                onValueChange = {
                    subtitle = it
                    subtitleError = subtitle.length > 120
                },
                label = { Text("Details", color = Color(0xFFA54A6F)) },
                isError = subtitleError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFA54A6F),
                    unfocusedTextColor = Color(0xFFA54A6F),
                    cursorColor = Color(0xFFA54A6F),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (subtitleError) {
            Text(
                text = "Details text must not exceed 120 characters",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank() && subtitle.isNotBlank() && title.length in 3..50 && subtitle.length <= 120) {
                    noteItem.title = title
                    noteItem.subtitle = subtitle
                    navController.popBackStack()
                } else {
                    titleError = true
                    subtitleError = true
                }
            },
                colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA54A6F), // Same as FloatingActionButton color
                contentColor = Color.White         // Text color
            ),
                modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 16.dp)
            ) {
                Text("Save Note")
            }
        }
    }
}