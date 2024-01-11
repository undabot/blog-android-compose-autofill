package com.undabot.composeAutofill.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.undabot.composeAutofill.ui.login.inputField.EmailInputField
import com.undabot.composeAutofill.ui.login.inputField.PasswordInputField
import com.undabot.composeAutofill.ui.utils.hideViewKeyboard
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
  viewModel: LoginViewModel,
  onLoginSuccess: () -> Unit,
) {
  val state by viewModel.state.collectAsState()
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    viewModel.onLoginSuccessEvent.collectLatest {
      context.hideViewKeyboard()
      onLoginSuccess()
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Compose autofill demo")
        }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      EmailInputField(
        value = state.emailValue,
        onValueChange = { viewModel.onEmailValueChanged(it) },
      )
      PasswordInputField(
        modifier = Modifier
          .padding(top = 10.dp),
        value = state.passwordValue,
        passwordHidden = state.passwordHidden,
        onValueChange = { viewModel.onPasswordValueChanged(it) },
        onPasswordHiddenChange = { viewModel.onPasswordHiddenChanged(it) },
      )
      Button(
        modifier = Modifier.padding(top = 10.dp),
        onClick = { viewModel.onLoginClicked() },
      ) {
        Text(text = "Login")
      }
    }
  }
}
