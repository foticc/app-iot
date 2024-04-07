package com.foticc.iot.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.foticc.iot.data.BottomItem
import com.foticc.iot.ui.page.ScannerScreen
import com.foticc.iot.ui.page.ThreePage
import com.foticc.iot.ui.theme.AppiotTheme
import com.foticc.iot.ui.theme.Purple40
import com.foticc.iot.ui.unprocessed.UnProcessScreen
import com.foticc.iot.ui.unprocessed.UnprocessedItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppiotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainFrame(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomItem.Home.route
    ) {
        composable(BottomItem.Scanner.route) {
            ScannerScreen()
        }
        composable(BottomItem.Home.route) {
//            Home(navController)
            UnProcessScreen(navController)
        }

//        navigation(startDestination = "main", route = "home") {
//
//            composable("info") {
//                ItemInfo()
//            }
//        }
        composable(BottomItem.Collections.route) {
            ThreePage()
        }
        composable("unprocessed") {
            UnprocessedItem(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController) {
    Scaffold(bottomBar = { BottomBar(navController) }) {
        MainFrame(Modifier.padding(it),navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomItem.Scanner,
        BottomItem.Home,
        BottomItem.Collections
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    if (currentRoute != null) {
        Log.d("currentRoute", currentRoute)
    }
    BottomAppBar {
        items.forEach {
            BottomNavigationItem(selected = currentRoute == it.route,
                icon = { Icon(imageVector = it.icon, contentDescription = it.title ) },
                selectedContentColor = Purple40,
                unselectedContentColor = Color.White,
                onClick = {
                    val route = navController.currentDestination?.route
                    if (!route.equals(it.route)) {
                        navController.navigate(it.route)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarItem(text:String) {
    Text(text = text,Modifier.clickable(onClick = {

    }))
}



@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppiotTheme {
        Box(modifier = Modifier.fillMaxSize()){
            val int = 1
            Text(text = "background", modifier = Modifier
                .background(Color.LightGray)
                .fillMaxSize())
            Text(text = "button", modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp))
        }

    }
}