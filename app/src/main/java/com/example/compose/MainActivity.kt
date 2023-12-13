package com.example.compose

import android.R.color
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import com.example.compose.ui.theme.ComposeTheme
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // check that location permission is granted
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val getLocation: () -> LocationData = {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.KEY_PROVIDER_ENABLED)
            }
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.EXTRA_PROVIDER_ENABLED)
            }
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            }
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            if (location != null) {
                LocationData(
                    location.latitude,
                    location.longitude,
                    location.altitude,
                    location.speed,
                    location.accuracy,
                    location.time,
                    location.bearing,
                    location.provider?:""
                )
            } else {
                LocationData(-1.0, -1.0, 0.0, 0.0f, 0.0f, 0L, 0.0f, "")
            }
        }

        // a coroutine that uploads location data to server every 50 seconds
        val locationCoroutine = kotlinx.coroutines.GlobalScope.launch {
            while (true) {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val altitude = location.altitude
                    val speed = location.speed
                    val accuracy = location.accuracy
                    val time = location.time
                    val bearing = location.bearing
                    val provider = location.provider
                    val locationData = LocationData(latitude, longitude, altitude, speed, accuracy, time, bearing, provider?:"")
                    val locationDataJson = locationData.toJson()
                    val response = uploadLocationData(locationDataJson)
                }
                kotlinx.coroutines.delay(50000)
            }
        }

        setContent {

            var stage by remember { mutableStateOf(false) }
            val stageIncrement: () -> Unit = {
                stage = true
            }
            ComposeTheme {
                when (stage) {
                    false -> SimpleImageCard(onClick = stageIncrement, location = getLocation())
                    true ->
                        MainView(MainViewModel(getLocation()))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleImageCard(padding: Dp = 8.dp, onClick: () -> Unit, location: LocationData = LocationData(0.0, 0.0, 0.0, 0.0f, 0.0f, 0L, 0.0f, "")) {
    Column (
//        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedCard(onClick = onClick) {
            FrontPageImage(onClick = onClick)
        }

        // a text message displaying location. text color should be black
        Text(
            text = "Location: (${location.latitude}, ${location.longitude})",
            modifier = Modifier
        )
    }
}

@Composable
fun FrontPageImage(onClick: () -> Unit = {}) {
//    SquareClippedImage(painter = painterResource(R.drawable.pku), contentDescription = "pku", modifier = Modifier.clickable(onClick = onClick), size = 10.dp)
    Box( // a square box that fills max width
        modifier = Modifier
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(R.drawable.pku),
            contentDescription = "pku",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clickable(onClick = onClick)
        )
    }
}

// a front-page view with home page image('pku') and text ("Hello, World!"), where the "World!" bit is clickable to let user enter username.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrontPageView(username: String = "World", onUsernameChange: (String) -> Unit, stage: Int = 1) {
    var stageLocal = stage
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FrontPageImage(onClick = { stageLocal += 1 })


                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var isTyping by remember { mutableStateOf(false) }


                    Text(
                        text = "Hello,",
                        modifier = Modifier
                            .clip(RectangleShape)
                    )

                    when (isTyping) {
                        false -> Text(
                            text = "World!",
                            modifier = Modifier
                                .clip(RectangleShape)
                                .clickable(onClick = { isTyping = true })
                        )
                        // if true, a input field for user to enter username. username will be returned with passed callback.
                        true -> {
                            TextField(
                                value = username,
                                onValueChange = { onUsernameChange(it) },
                                label = { username },
                                maxLines = 2,
                                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                            )
                        }
                    }

                    Text(
                        text = "!",
                        modifier = Modifier
                            .clip(RectangleShape)
                    )
                }

                // repeat UselessSwitch() stageLocal - 1 times, and updates when stageLocal increases.
                for (i in 0 until stageLocal) {
                    UselessSwitch()
                }
            }
        }
    }
}


// a useless switch. when switch is false, a text saying "Here is a useless switch." will be shown. when switch is true, a text saying "This switch is useless." will be shown.
@Composable
fun UselessSwitch() {
    var switch by remember { mutableStateOf(false) }
    val onSwitchChange: (Boolean) -> Unit = {
        switch = it
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // a text saying "Here is a useless switch." if switch is false, or "This switch is useless." if switch is true.
        when (switch) {
            false -> Text(
                text = "Here is a useless switch.",
                modifier = Modifier
                    .clip(RectangleShape)
            )
            true -> Text(
                text = "This switch is useless.",
                modifier = Modifier
                    .clip(RectangleShape)
            )
        }

        // a switch from material3
        Switch(
            checked = switch,
            onCheckedChange = onSwitchChange,
            modifier = Modifier
                .padding(8.dp)
                .clip(RectangleShape)
        )
    }
}