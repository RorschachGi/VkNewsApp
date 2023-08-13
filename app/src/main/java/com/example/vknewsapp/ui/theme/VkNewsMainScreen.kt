package com.example.vknewsapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vknewsapp.MainViewModel
import com.example.vknewsapp.navigation.AppNavGraph
import com.example.vknewsapp.navigation.NavigationState
import com.example.vknewsapp.navigation.Screen
import com.example.vknewsapp.navigation.rememberNavigationState


@Composable
fun MainScreen(viewModel: MainViewModel){
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            BottomNavigation{
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                                  navigationState.navigateTo(item.screen.route)
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) {paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = { HomeScreen(viewModel = viewModel, paddingValues = paddingValues) },
            favouriteScreenContent = { TextCounter(name = "Favourite") },
            profileScreenContent = {TextCounter(name = "Profile") }
        )
    }
}

@Composable
private fun TextCounter(name: String){
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable {count++},
        text = "$name Count: $count",
        color = Color.Black)
}