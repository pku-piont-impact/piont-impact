package com.example.compose

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import com.example.compose.ui.theme.ComposeTheme
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import retrofit2.http.GET

import com.google.gson.annotations.SerializedName

public data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val speed: Float,
    val accuracy: Float,
    val time: Long,
    val bearing: Float,
    val provider: String
) {
    fun toJson(): String {
        return "{\"latitude\":$latitude,\"longitude\":$longitude,\"altitude\":$altitude,\"speed\":$speed,\"accuracy\":$accuracy,\"time\":$time,\"bearing\":$bearing,\"provider\":\"$provider\"}"
    }
}

class PersonalView : ComponentActivity() {
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


data class RandomNumberPairsResponse(
    @SerializedName("random_number_pairs")
    val randomNumberPairs: List<List<Double>>
)

public interface LambdaService {
    @GET("/default/myFunctionName")
    suspend fun getRandomNumberPairs(): RandomNumberPairsResponse
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
            text = "Location: (${location.latitude}, ${location.longitude})\n Provider: ${location.provider}",
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
