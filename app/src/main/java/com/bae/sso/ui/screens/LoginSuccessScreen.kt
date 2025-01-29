package com.bae.sso.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bae.sso.R
import com.bae.sso.ui.components.ExpandableText
import com.bae.sso.ui.theme.SSOTheme

@Composable
fun LoginSuccessScreen(
    email: String,
    token: String,
    givenName: String?,
    familyName: String?,
    userImage: String?,
    phoneNumber: String?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LoginSuccessScreenContent(
                email = email,
                token = token,
                givenName = givenName,
                familyName = familyName,
                userImage = userImage,
                phoneNumber = phoneNumber
            )
        }
    }
}

@Composable
private fun LoginSuccessScreenContent(
    email: String,
    token: String,
    givenName: String?,
    familyName: String?,
    userImage: String?,
    phoneNumber: String?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (LocalInspectionMode.current) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(50.dp)
                    ),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } else {
            userImage?.let {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        ),
                    model = ImageRequest.Builder(context)
                        .data(userImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Name : $givenName  $familyName",
            style = TextStyle(
                color = colorResource(R.color.black),
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Start
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Email : $email",
            style = TextStyle(
                color = colorResource(R.color.black),
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Start
            )
        )

        phoneNumber?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Phone : $it",
                style = TextStyle(
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Start
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ExpandableText(
            text = "Token : $token"
        )
    }
}

@Preview
@Composable
private fun LoginSuccessScreenContentPreview() {
    SSOTheme {
        LoginSuccessScreenContent(
            email = "test@gmail.com",
            token = "token",
            givenName = "givenName",
            familyName = "familyName",
            userImage = "userImage",
            phoneNumber = "xxx-xxxx-xxxx"
        )
    }
}
