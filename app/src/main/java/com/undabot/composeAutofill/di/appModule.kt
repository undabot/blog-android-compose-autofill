package com.undabot.composeAutofill.di

import com.undabot.composeAutofill.AutofillManagerFactory
import com.undabot.composeAutofill.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
  viewModelOf(::LoginViewModel)
  factoryOf(::AutofillManagerFactory)
}
