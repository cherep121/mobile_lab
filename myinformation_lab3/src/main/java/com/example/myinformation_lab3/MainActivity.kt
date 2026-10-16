package com.example.myinformation_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFDFF7F2)) {
                    MyInformationScreen()
                }
            }
        }
    }
}

@Composable
fun MyInformationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(1.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF073042), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.avatar_initial),
                    color = Color.White,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(R.string.full_name),
                fontSize = 36.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.title),
                color = Color(0xFF006C4C),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ContactRow(symbol = "☎", value = stringResource(R.string.phone))
            ContactRow(symbol = "@", value = stringResource(R.string.social_handle))
            ContactRow(symbol = "✉", value = stringResource(R.string.email))
        }
    }
}

@Composable
private fun ContactRow(symbol: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = symbol, color = Color(0xFF006C4C), fontSize = 22.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = value, fontSize = 16.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyInformationPreview() {
    MaterialTheme { MyInformationScreen() }
}
