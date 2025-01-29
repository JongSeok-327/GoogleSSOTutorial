package com.bae.sso.ui.components

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bae.sso.R
import com.bae.sso.ui.theme.SSOTheme

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    @ColorRes strokeColor: Int = R.color.white,
    @ColorRes buttonColor: Int = R.color.white,
    @ColorRes textColor: Int = R.color.black,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(strokeColor)
        ),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(buttonColor)),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "Google Icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sign in with Google",
                style = TextStyle(
                    color = colorResource(textColor),
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
fun SignUpWithSavePasswordButton(
    modifier: Modifier = Modifier,
    @ColorRes strokeColor: Int = R.color.white,
    @ColorRes buttonColor: Int = R.color.white,
    @ColorRes textColor: Int = R.color.black,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(strokeColor)
        ),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(buttonColor)),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_passkey),
                contentDescription = "Google Icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sign up with a save password",
                style = TextStyle(
                    color = colorResource(textColor),
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
fun ShowSavedPasswordButton(
    modifier: Modifier = Modifier,
    @ColorRes strokeColor: Int = R.color.white,
    @ColorRes buttonColor: Int = R.color.teal_200,
    @ColorRes textColor: Int = R.color.black,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(strokeColor)
        ),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(buttonColor)),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Show Saved Password",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Show Saved Password",
                style = TextStyle(
                    color = colorResource(textColor),
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview
@Composable
private fun GoogleSignInButtonPreview() {
    SSOTheme {
        GoogleSignInButton(
            modifier = Modifier,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun SignUpWithSavePasswordButtonPreview() {
    SSOTheme {
        SignUpWithSavePasswordButton(
            modifier = Modifier,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun ShowSavedPasswordButtonPreview() {
    SSOTheme {
        ShowSavedPasswordButton(
            modifier = Modifier,
            onClick = {}
        )
    }
}