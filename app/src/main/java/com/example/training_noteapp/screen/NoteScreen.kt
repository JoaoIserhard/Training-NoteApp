package com.example.training_noteapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.training_noteapp.R
import com.example.training_noteapp.components.NoteButton
import com.example.training_noteapp.components.NoteInputText
import com.example.training_noteapp.model.Note
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit

) {
    val titleLimit = 25
    val descriptionLimit = 150

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var showErrors by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notification Icon"
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NoteInputText(
                    text = title,
                    label = "Title",
                    charLimit = titleLimit,
                    isError = showErrors && title.isBlank(),
                    errorText = "Title cannot be empty",
                    onTextChange = {
                        title = it
                        if (it.isNotBlank()) showErrors = false
                    }
                )
                NoteInputText(
                    text = description,
                    label = "Note description",
                    charLimit = descriptionLimit,
                    isError = showErrors && description.isBlank(),
                    errorText = "Description cannot be empty",
                    maxLines = 6,
                    onTextChange = {
                        description = it
                        if (it.isNotBlank()) showErrors = false
                    }
                )
                NoteButton(
                    text = "Save",
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            onAddNote(
                                Note(
                                    title = title,
                                    description = description
                                )
                            )
                            title = ""
                            description = ""
                            showErrors = false
                            focusManager.clearFocus()
                            Toast.makeText(context,"Note Added", Toast.LENGTH_SHORT).show()
                        } else {
                            showErrors = true
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
            HorizontalDivider(modifier = Modifier.padding(8.dp))
            LazyColumn {
                items(notes) { note ->
                    NoteRow(
                        note = note,
                        onNoteClicked = {
                            onRemoveNote(note)
                        })
                }
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("EEE, d MMM", java.util.Locale.getDefault())
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier
                .clickable { onNoteClicked(note) }
                .padding(12.dp),

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatter.format(note.entryDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = emptyList(), onAddNote = {}, onRemoveNote = {})
}