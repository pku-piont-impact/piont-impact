package com.example.compose

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

@Composable
public fun Greetings() {
    var username by remember { mutableStateOf("World") }
    val onUsernameChange: (String) -> Unit = {
        username = it
    }

    ComposeTheme {
        val padding = 8.dp
        Surface (
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()

        ){
            var stage by remember { mutableStateOf(0) }
            val stageIncrement: () -> Unit = {
                stage += 1
            }

            when (stage) {
                0 -> SimpleImageCard(onClick = stageIncrement)
                else -> FrontPageView(onUsernameChange = onUsernameChange)
            }
        }
    }
}


@Composable
fun SimpleImageCard(padding: Dp = 8.dp, onClick: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card {
            FrontPageImage(onClick = onClick)
        }
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
            Column {
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
                                modifier = Modifier.padding(20.dp)
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