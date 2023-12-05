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
import androidx.compose.material.icons.filled.Face



@Composable
public fun MapView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconLayer()
        DensityLayer()
        MapLayer()
    }
}


@Composable
fun IconLayer() {
    Box(modifier = Modifier.fillMaxSize()) {
        // this icon should be at the centre
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "User Location",
            // change the color of icon to white
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .padding(10.dp)
                .align(Alignment.Center)
                .background(color = Color.White.copy(alpha = 0.3f))
        )
    }
}


@Composable
fun DensityLayer() {

}


@Composable
fun MapLayer() {
    
}