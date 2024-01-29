package com.example.navhostscreenapp.ui.login

import android.net.InetAddresses
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.navhostscreenapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.InetAddress


@Preview
@Composable
fun MainPage() {


    val localCore = rememberCoroutineScope()

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var loading by remember { mutableStateOf(false) }

    var passwordVisualTransformation by remember {
        mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
    }

    var hasError by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        LoginAnim(modifier = Modifier.height(280.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "username-i18n") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountBox,
                    contentDescription = "USER"
                )
            },
            singleLine = true,
            value = username,
            isError = hasError,
            onValueChange = { username = it })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "password-i18n") },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "USER") },
            visualTransformation = passwordVisualTransformation,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "show or hide password",
                    modifier = Modifier.clickable(onClick = {
                        passwordVisualTransformation =
                            if (passwordVisualTransformation != VisualTransformation.None) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }
                    })
                )
            },
            isError = hasError,
            value = password, onValueChange = {
                password = it
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .height(50.dp),
            onClick = {
                loading = true
                hasError = !inVaild(username, password)
                localCore.launch {
                    delay(1000)
                    loading = false
                }
            }) {
            if (!loading) {
                Text(
                    modifier = Modifier,
                    text = "login",
                    color = MaterialTheme.colorScheme.primaryContainer

                )
            } else {
                Text(text = "...", color = MaterialTheme.colorScheme.primaryContainer)
            }
        }
    }
}

fun inVaild(username: String, password: String): Boolean {
    return username.isNotBlank() && password.isNotBlank();
}

fun getIpAddress(): String {
    return InetAddress.getLocalHost().hostAddress;
}

@Composable
fun LoginAnim(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim))
    LottieAnimation(modifier = modifier, composition = composition, iterations = Int.MAX_VALUE)
}