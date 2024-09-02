package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.screens.mainCard.MainCard
import com.example.screens.tabLayout.TabLayout
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                getData("London",this)
                Image(
                    painter = painterResource(id = R.drawable.background_app),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard()
                    TabLayout()
                }
            }
        }
    }
}

private fun getData(city: String, context: Context) {
    val url = "https://weatherapi-com.p.rapidapi.com/forecast.json?q=$city&days=3"
    val queue = Volley.newRequestQueue(context)
    val sRequest = object : StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.d("ololo", "getData:$response ")
        },
        {
            Log.d("ololo", "getError:$it ")
        }) {
        override fun getHeaders(): Map<String, String> {
            return mapOf(
                "x-rapidapi-key" to "d24aa51519mshc01c17fefe7eb68p129933jsn1e3c2b91e8fc",
                "x-rapidapi-host" to "weatherapi-com.p.rapidapi.com"
            )
        }
    }
    queue.add(sRequest)
}

