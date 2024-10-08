package com.example.weatherapp.screens.mainCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.data.WeatherModel
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun MainCard(currentDay: MutableState<WeatherModel>) {
    val minTemp = currentDay.value.minTemp
    val maxTemp = currentDay.value.maxTemp
    val minMaxTemp =
        if (minTemp.isNotEmpty() && maxTemp.isNotEmpty()) {
           "${minTemp.toFloat().toInt()}/${maxTemp.toFloat().toInt()}"
        } else {
            "$minTemp/$maxTemp"
        }
    Column(
        modifier = Modifier
            .padding(6.dp)

    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(colorResource(id = R.color.blue_star)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentDay.value.time,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white)
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.conditionIcon}",
                        contentDescription = null,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Text(
                    text = currentDay.value.city,
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.white),

                    )
                Text(
                    text = "${currentDay.value.currentTemp?:null} °C",
                    fontSize = 44.sp,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = currentDay.value.conditionText,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "$minMaxTemp °C",
                        color = colorResource(id = R.color.white),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_update_weather),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

