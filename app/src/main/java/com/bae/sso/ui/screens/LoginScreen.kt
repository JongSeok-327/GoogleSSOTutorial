package com.bae.sso.ui.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bae.sso.R
import com.bae.sso.ui.SCREEN_LOGIN_SUCCESS
import com.bae.sso.ui.SCREEN_SIGN_UP
import com.bae.sso.ui.components.GoogleSignInButton
import com.bae.sso.ui.components.SignUpWithSavePasswordButton
import com.bae.sso.ui.theme.SSOTheme
import com.bae.sso.util.AccountHelper
import com.bae.sso.util.GoogleSingleSignOnResult
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current as ComponentActivity
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LoginScreenContent(
                signInOnClick = {
                    coroutineScope.launch {
                        AccountHelper(context)
                            .googleSingleSignOn()
                            .takeIf { it is GoogleSingleSignOnResult.Success }
                            ?.let { result ->
                                val successResult = result as GoogleSingleSignOnResult.Success
                                navController.navigate(
                                    SCREEN_LOGIN_SUCCESS(
                                        email = successResult.email,
                                        token = successResult.token,
                                        givenName = successResult.givenName,
                                        familyName = successResult.familyName,
                                        userImage = successResult.userImage,
                                        phoneNumber = successResult.phoneNumber
                                    )
                                )
                            } ?: run {
                            Log.e("GoogleSignIn", "Sign-in failed or cancelled")
                        }
                    }
                },
                signInOneTapOnClick = {
                    coroutineScope.launch {
                        AccountHelper(context)
                            .googleOneTapSignOn()
                            .takeIf { it is GoogleSingleSignOnResult.Success }
                            ?.let { result ->
                                val successResult = result as GoogleSingleSignOnResult.Success
                                navController.navigate(
                                    SCREEN_LOGIN_SUCCESS(
                                        email = successResult.email,
                                        token = successResult.token,
                                        givenName = successResult.givenName,
                                        familyName = successResult.familyName,
                                        userImage = successResult.userImage,
                                        phoneNumber = successResult.phoneNumber
                                    )
                                )
                            } ?: run {
                            Log.e("GoogleSignIn", "Sign-in one tap failed or cancelled")
                        }
                    }
                },
                signUpWithSavePassWordOnClick = {
                    navController.navigate(SCREEN_SIGN_UP)
                }
            )
        }
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    signInOnClick: () -> Unit = {},
    signInOneTapOnClick: () -> Unit = {},
    signUpWithSavePassWordOnClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleSignInButton(
            buttonColor = R.color.black,
            textColor = R.color.gray_text,
            strokeColor = R.color.gray_stroke,
            onClick = signInOnClick,
            type = 0
        )

        Spacer(modifier = Modifier.height(10.dp))

        GoogleSignInButton(
            buttonColor = R.color.purple_500,
            textColor = R.color.gray_text,
            strokeColor = R.color.gray_stroke,
            onClick = signInOneTapOnClick,
            type = 1
        )

        Spacer(modifier = Modifier.height(10.dp))

        SignUpWithSavePasswordButton(
            buttonColor = R.color.pink_button,
            textColor = R.color.gray_text,
            strokeColor = R.color.gray_stroke,
            onClick = signUpWithSavePassWordOnClick
        )
    }
}

@Preview
@Composable
private fun LoginScreenContentPreview() {
    SSOTheme {
        LoginScreenContent()
    }
}