package com.undabot.composeAutofill

import android.content.Context
import android.os.Build
import android.view.autofill.AutofillManager

class AutofillManagerFactory(
  private val context: Context,
) {

  fun create(): AutofillManager? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    context.getSystemService(AutofillManager::class.java)
  } else {
    null
  }
}
