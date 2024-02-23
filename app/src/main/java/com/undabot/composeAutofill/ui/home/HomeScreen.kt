package com.undabot.composeAutofill.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Compose autofill demo")
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {
      Text(text = "Home screen")
    }
  }
}
