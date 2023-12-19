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
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.random.Random
public class MainViewModel(private val locationData: LocationData): ViewModel() {
    // location is initialized from an outside parameter
    val location = MutableLiveData(listOf(-2.0, -2.0))

    val locationList = MutableLiveData(listOf(listOf(39.9869, 116.3059), listOf(39.9899, 116.3039), listOf(39.9862, 116.3099)))

    private fun fetchList() {
        viewModelScope.launch(Dispatchers.IO) {
            // 创建一个Retrofit对象，用于与亚马逊的API进行通信
            val retrofit = Retrofit.Builder()
                .baseUrl("https://ginzcyijvh.execute-api.ap-southeast-2.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            // 创建一个LambdaService对象，用于调用亚马逊的API接口
            val service = retrofit.create(LambdaService::class.java)
            if (true) {
                //use retrofit to get random number pairs from server
                try {
                    val randomNumberPairs = service.getRandomNumberPairs()
                    Log.w("WarningTag", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!. " + randomNumberPairs.randomNumberPairs.toString())
                    withContext(Dispatchers.Main) {
                        // Update UI with the fetched data
                        locationList.postValue(randomNumberPairs.randomNumberPairs)
                    }
                } catch (e: Exception) {
                    locationList.postValue(listOf(listOf(Random.nextDouble(39.985861, 39.997237), Random.nextDouble(116.306257, 116.315872))))
                }
            }
        }
        // locationList.postValue(listOf(listOf(Random.nextDouble(39.985861, 39.997237), Random.nextDouble(116.306257, 116.315872))));
    }

    init {
        // change location to lontitude and latitude provided in locationData
        location.postValue(listOf(locationData.latitude, locationData.longitude))
        fetchList()
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