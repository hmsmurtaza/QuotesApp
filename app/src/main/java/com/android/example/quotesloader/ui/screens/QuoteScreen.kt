package com.android.example.quotesloader.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.quotesloader.ui.viewmodel.QuoteViewModel
import com.android.example.quotesloader.ui.viewmodel.UiState

@Composable
fun QuoteScreen(viewModel: QuoteViewModel = viewModel()) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadQuotes()
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val quotes = (uiState as UiState.Success).quotes
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(quotes) { quote ->
                    Text(text = ". $quote", modifier = Modifier.padding(8.dp))
                }
            }
        }

        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Error $message")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadQuotes() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun QuoteScreenPreview() {
    QuoteScreen()
}