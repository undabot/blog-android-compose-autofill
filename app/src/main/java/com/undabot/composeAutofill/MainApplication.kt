package com.undabot.composeAutofill

import android.app.Application
import com.undabot.composeAutofill.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@MainApplication)
      modules(appModule)
    }
  }
}
