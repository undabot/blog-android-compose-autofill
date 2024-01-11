package com.undabot.composeAutofill.ui.login

import android.os.Build
import androidx.lifecycle.ViewModel
import com.undabot.composeAutofill.AutofillManagerFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
  private val autofillManagerFactory: AutofillManagerFactory,
) : ViewModel() {

  var state = MutableStateFlow(State())

  private val _onLoginSuccessEvent = Channel<Unit>(Channel.BUFFERED)
  val onLoginSuccessEvent = _onLoginSuccessEvent.receiveAsFlow()

  fun onEmailValueChanged(value: String) {
    state.update { it.copy(emailValue = value) }
  }

  fun onPasswordValueChanged(value: String) {
    state.update { it.copy(passwordValue = value) }
  }

  fun onPasswordHiddenChanged(value: Boolean) {
    state.update { it.copy(passwordHidden = value) }
  }

  fun onLoginClicked() {
    val emailValue = state.value.emailValue
    val passwordValue = state.value.passwordValue
    if (emailValue.isNotBlank() && passwordValue.isNotBlank()) {
      storePasswordForAutofill()
      _onLoginSuccessEvent.trySend(Unit)
    }
  }

  private fun storePasswordForAutofill() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      autofillManagerFactory.create()?.commit()
    }
  }

  data class State(
    val emailValue: String = "",
    val passwordValue: String = "",
    val passwordHidden: Boolean = true,
  )
}
