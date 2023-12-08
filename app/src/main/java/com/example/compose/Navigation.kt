package com.example.compose

import android.content.Context
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem

import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.compose.runtime.Composable
import com.example.compose.ui.theme.ComposeTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import androidx.annotation.StringRes
import androidx.annotation.DrawableRes
import androidx.navigation.NavGraph.Companion.findStartDestination



import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

public class MainViewModel: ViewModel() {
    val location = MutableLiveData(listOf(39.9869, 116.3059))

    // get location of device
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(newLocation: Location) {
            location.value = listOf(newLocation.latitude, newLocation.longitude)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            // Handle provider status changes
        }

        override fun onProviderEnabled(provider: String) {
            // Handle provider being enabled
        }

        override fun onProviderDisabled(provider: String) {
            // Handle provider being disabled
        }
    }

    val locationList = MutableLiveData(listOf(listOf(39.9869, 116.3059), listOf(39.9899, 116.3039), listOf(39.9862, 116.3099)))

    init {
        viewModelScope.launch {
//            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener)
        }
    }
}


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground,
//    icon: Painter = painterResource(R.drawable.ic_launcher_foreground)
) {
    object Map: Screen("Map", R.string.map)
    object Chat: Screen("Chat", R.string.chat)
}

val items = listOf(
    Screen.Map,
    Screen.Chat,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun MainView(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = screen.icon), contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Map.route, Modifier.padding(innerPadding)) {
            composable(Screen.Map.route) { MapView(mainViewModel) }
            composable(Screen.Chat.route) { ChatView() }
        }
    }
}










@Composable
public fun MainViewOld(username: String = "World") {
    val maxState = 4
    var state by remember {
        mutableIntStateOf(0)
    }
    val stateIncrement: () -> Unit = {
        state += 1
        if (state >= maxState) {
            state = 0
        }
    }
    val stateDecrement: () -> Unit = {
        state -= 1
        if (state < 0) {
            state = maxState - 1
        }
    }
    val stateChange: (Int) -> Unit = {
        state = it
    }

    when (state) {
        0 -> MapView(mainViewModel = MainViewModel())
        1 -> ChatView()
        2 -> BrowseView()
        3 -> UserView()
    }


}