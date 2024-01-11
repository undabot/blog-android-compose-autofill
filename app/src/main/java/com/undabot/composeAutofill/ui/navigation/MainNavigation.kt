package com.undabot.composeAutofill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.undabot.composeAutofill.ui.home.HomeScreen
import com.undabot.composeAutofill.ui.login.LoginScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavigation() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "login") {
    composable("login") {
      LoginScreen(
        viewModel = koinViewModel(),
        onLoginSuccess = {
          navController.navigate(
            route = "home",
          ) {
            popUpTo(
              route = "login",
            ) {
              inclusive = true
            }
          }
        },
      )
    }
    composable("home") {
      HomeScreen()
    }
  }
}
