package com.example.navhostscreenapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.navhostscreenapp.data.BottomItem
import com.example.navhostscreenapp.page.Home
import com.example.navhostscreenapp.page.ItemInfo
import com.example.navhostscreenapp.page.ThreePage
import com.example.navhostscreenapp.page.TwoPage
import com.example.navhostscreenapp.ui.theme.NavhostScreenAppTheme
import com.example.navhostscreenapp.ui.theme.Purple40
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavhostScreenAppTheme {
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
        startDestination = "home"
    ) {
        composable("scanner") {
            TwoPage()
        }
        navigation(startDestination = "main", route = "home") {
            composable("main") {
                Home(navController)
            }
            composable("info") {
                ItemInfo()
            }
        }
        composable("collections") {
            ThreePage()
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
                onClick = { navController.navigate(it.route) }
            )
        }
    }
}

@Composable
fun BottomBarItem(text:String) {
    Text(text = text,Modifier.clickable(onClick = {

    }))
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavhostScreenAppTheme {
        val navController = rememberNavController()
        App(navController)
    }
}