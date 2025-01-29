package com.bae.sso.ui.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.bae.sso.R
import com.bae.sso.ui.components.ShowSavedPasswordButton
import com.bae.sso.ui.components.SignUpWithSavePasswordButton
import com.bae.sso.ui.theme.SSOTheme
import com.bae.sso.util.AccountHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sign Up with a passkey",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W700
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = dropUnlessResumed { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SignUpScreenContent(
                onSignUpClick = { email, password ->
                    coroutineScope.launch {
                        val result = AccountHelper(context).signUpWithSavePassword(
                            email = email,
                            password = password
                        )
                        Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                    }
                },
                getPasswordClick = {
                    coroutineScope.launch {
                        AccountHelper(context).getSavedPassword()
                    }
                }
            )
        }
    }
}

@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    onSignUpClick: (String, String) -> Unit = { _, _ -> },
    getPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("pw") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SignUpWithSavePasswordButton(
            modifier = Modifier.fillMaxWidth(),
            buttonColor = R.color.pink_button,
            textColor = R.color.gray_text,
            strokeColor = R.color.gray_stroke,
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Input email and pw"
                } else {
                    errorMessage = ""
                    onSignUpClick(email, password)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        ShowSavedPasswordButton(onClick = getPasswordClick)
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SSOTheme {
        SignUpScreenContent()
    }
}