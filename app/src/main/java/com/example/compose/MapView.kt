package com.example.compose

import androidx.compose.runtime.Composable
import com.example.compose.ui.theme.ComposeTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.LiveData

import androidx.compose.ui.geometry.Offset

typealias Location = List<Double>
val defaultLocation: Location = listOf(39.9869, 116.3059)


@Composable
public fun MapView(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DensityLayer(mainViewModel = mainViewModel)
        MapLayer()
    }
}


@Composable
fun centreIcon() {
    Box(modifier = Modifier.fillMaxSize()) {
        // this icon should be at the centre
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "User Location",
            // change the color of icon to white
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .align(Alignment.Center)
                .background(color = Color.White.copy(alpha = 0.3f))
        )
    }
}

@Composable
fun crowdIcon(pos: Location,
              scaling: Int = 20000,
              imageVector: ImageVector = Icons.Filled.Face,
              backgroundColor: Color = Color.White.copy(alpha = 0.3f),
              size: Dp = 50.dp,
              padding: Dp = 10.dp) {
    Box(modifier = Modifier.fillMaxSize()) {
        // this icon should be at the centre
        Icon(
            imageVector = imageVector,
            contentDescription = "Friend Location",
            // change the color of icon to white
            modifier = Modifier
                .size(size)
                .padding(padding)
                .align(Alignment.Center)
                .offset(x = (pos.last() * scaling).toInt().dp,
                        y = (-pos.first() * scaling).toInt().dp)
                .background(color = backgroundColor)
        )
    }
}

// convert degree minute second to decimal degree
fun dms2dd(dms: List<Double>): Double {
    return dms[0] + dms[1] / 60 + dms[2] / 3600
}

val landmarkList = listOf(
    listOf(dms2dd(listOf(39.0, 59.0, 34.2)), dms2dd(listOf(116.0, 18.0, 36.3))), // 39°59'34.2"N 116°18'36.3"E // 1TB-LU
    listOf(dms2dd(listOf(39.0, 59.0, 33.6)), dms2dd(listOf(116.0, 18.0, 36.3))), // 39°59'33.6"N 116°18'36.3"E // 1TB-LD
    listOf(39.992869, 116.310739), // 1TB-RU
    listOf(dms2dd(listOf(39.0, 59.0, 33.7)), dms2dd(listOf(116.0, 18.0, 38.7))), // 39°59'33.7"N 116°18'38.7"E 1TB-RD
    listOf(39.988552, 116.312713), // 54-LU
    listOf(39.988557, 116.314205), // 54-RU
    listOf(39.986978, 116.312837), // 54-LD
    listOf(39.986949, 116.314194), // 54-RD
    listOf(39.987772, 116.308593), // 35-LU
    listOf(39.987784, 116.309457), // 35-RU
    listOf(39.987414, 116.309452), // 35-RD
)


@Composable
fun LandmarkLayer(myLocation: Location) {
    for (location in landmarkList) {
        crowdIcon(
            pos = listOf(location.first() - myLocation.first(),
            location.last() - myLocation.last()),
            imageVector = Icons.Filled.Add,
            size = 30.dp,
            backgroundColor = Color.White.copy(alpha = 0.3f))
    }
}

@Composable
// myLocation is a livedata
fun DensityLayer(mainViewModel: MainViewModel) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    Box(
        modifier = Modifier.fillMaxSize()
            .graphicsLayer(
                // Apply the scale and translation
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    // Adding the pan offset to the current offset
                    offset += pan
                }
            }
    ) {
        centreIcon()
        LandmarkLayer(myLocation = mainViewModel.location.value ?: defaultLocation)

        val myLocation = mainViewModel.location.value ?: defaultLocation
        // make locationList not nullable
        val locationList = mainViewModel.locationList.value ?: listOf(defaultLocation)

        for (p in locationList) {
            crowdIcon(pos = listOf(p.first() - myLocation.first(), p.last() - myLocation.last()))
        }
    }
}


@Composable
fun MapLayer() {
    
}