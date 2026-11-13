package com.example.soapp.feature.createpost.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onPostCreated: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel = viewModel(factory = CreatePostViewModel.provideFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onPostCreated()
            onDismiss()
        }
    }

    if (uiState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(CreatePostUiEvent.DismissError) },
            title = { Text("ข้อผิดพลาด", fontWeight = FontWeight.Bold) },
            text = { Text(uiState.errorMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(CreatePostUiEvent.DismissError) }) {
                    Text("ตกลง")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text("สร้างโพสต์ใหม่ 📝", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "ปิด")
                    }
                },
                actions = {
                    Button(
                        onClick = { viewModel.onEvent(CreatePostUiEvent.PublishPost) },
                        enabled = uiState.isValid && !uiState.isPublishing,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        if (uiState.isPublishing) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        } else {
                            Text("โพสต์")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.content,
                onValueChange = { viewModel.onEvent(CreatePostUiEvent.UpdateContent(it)) },
                placeholder = { Text("คุณกำลังคิดอะไรอยู่?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                enabled = !uiState.isPublishing
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${uiState.remainingChars} ตัวอักษร",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (uiState.remainingChars < 0) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
