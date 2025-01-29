package com.bae.sso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bae.sso.ui.SCREEN_LOGIN
import com.bae.sso.ui.SCREEN_LOGIN_SUCCESS
import com.bae.sso.ui.SCREEN_SIGN_UP
import com.bae.sso.ui.screens.LoginScreen
import com.bae.sso.ui.screens.LoginSuccessScreen
import com.bae.sso.ui.screens.SignUpScreen
import com.bae.sso.ui.theme.SSOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SSOTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SCREEN_LOGIN
                ) {
                    composable<SCREEN_LOGIN> {
                        LoginScreen(navController)
                    }

                    composable<SCREEN_LOGIN_SUCCESS> {
                        val args = it.toRoute<SCREEN_LOGIN_SUCCESS>()
                        LoginSuccessScreen(
                            email = args.email,
                            token = args.token,
                            givenName = args.givenName,
                            familyName = args.familyName,
                            userImage = args.userImage,
                            phoneNumber = args.phoneNumber
                        )
                    }

                    composable<SCREEN_SIGN_UP> {
                        SignUpScreen(navController)
                    }
                }
            }
        }
    }
}