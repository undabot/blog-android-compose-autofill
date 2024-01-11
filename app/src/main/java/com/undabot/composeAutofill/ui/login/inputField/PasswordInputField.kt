package com.undabot.composeAutofill.ui.login.inputField

import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.undabot.composeAutofill.R

@Composable
fun PasswordInputField(
  value: String,
  passwordHidden: Boolean,
  modifier: Modifier = Modifier,
  onValueChange: (newValue: String) -> Unit,
  onPasswordHiddenChange: (newValue: Boolean) -> Unit,
) {
  val lifecycleOwner = LocalLifecycleOwner.current
  var editText: EditText? by remember { mutableStateOf(null) }
  var inFocus by remember { mutableStateOf(false) }

  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == Lifecycle.Event.ON_DESTROY) {
        editText?.setText("")
        editText = null
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  Box(
    modifier = modifier
      .clip(MaterialTheme.shapes.large)
      .border(
        width = if (inFocus) 2.dp else 1.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.large,
      ),
  ) {
    if (value.isEmpty()) {
      Text(
        modifier = Modifier
          .align(Alignment.CenterStart)
          .padding(start = 16.dp),
        text = "Enter password",
      )
    }
    AndroidView(
      modifier = Modifier.fillMaxWidth(),
      factory = { context ->
        val layout = LayoutInflater.from(context).inflate(R.layout.password_input_field, null)
        layout.findViewById<EditText>(R.id.passwordEditText).apply {
          setText(value)
          onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            inFocus = hasFocus
          }
          doAfterTextChanged {
            val newValue = editableText?.toString().orEmpty()
            onValueChange(newValue)
          }
          editText = this
        }
        layout
      },
      update = { layout ->
        layout.findViewById<EditText>(R.id.passwordEditText).apply {
          val newTransformationMethod = if (passwordHidden) {
            PasswordTransformationMethod.getInstance()
          } else {
            null
          }
          if (newTransformationMethod != transformationMethod) {
            val oldSelectionStart = selectionStart
            val oldSelectionEnd = selectionEnd
            transformationMethod = newTransformationMethod
            setSelection(oldSelectionStart, oldSelectionEnd)
          }
        }
      },
    )
    IconButton(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 8.dp),
      onClick = { onPasswordHiddenChange(!passwordHidden) },
    ) {
      Icon(
        painter = painterResource(
          id = if (passwordHidden) {
            R.drawable.ic_eye
          } else {
            R.drawable.ic_eye_crossed
          }
        ),
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = "Toggle password visibility",
      )
    }
  }
}
