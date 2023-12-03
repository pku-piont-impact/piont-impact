package com.example.compose

import android.R.color
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.clip
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), YOUR_REQUEST_CODE)
//        }

        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Preview
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column (modifier = modifier
        .fillMaxWidth() // It will fill the maximum available width
        .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "PIONT!",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp) // Apply a top and bottom padding
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "our blah blah blah ooo ooo ooo ooo ho ha ha crowd density ho ho ho ha!",
            color = MaterialTheme.colorScheme.secondary,
//            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
//            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.pku),
            contentDescription = "Front page picture",
            modifier = Modifier
//                .size(128.dp) // Specify size for the image
//                .clip(CircleShape) // Clip the image to a circle
                .padding(8.dp) // Add padding around the image
        )

        FilledButtonExample {

        }
    }
}

@Composable
fun FilledButtonExample(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Filled")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Greeting()
    }
}

