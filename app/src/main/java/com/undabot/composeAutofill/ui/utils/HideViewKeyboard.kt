package com.undabot.composeAutofill.ui.utils

import android.content.Context
import android.content.ContextWrapper
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity

fun Context.hideViewKeyboard() {
  val focusedView = getActivity()?.currentFocus
  if (focusedView != null && focusedView is EditText) {
    focusedView.clearFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(focusedView.windowToken, 0)
  }
}

private fun Context.getActivity(): ComponentActivity? = when (this) {
  is ComponentActivity -> this
  is ContextWrapper -> baseContext.getActivity()
  else -> null
}
