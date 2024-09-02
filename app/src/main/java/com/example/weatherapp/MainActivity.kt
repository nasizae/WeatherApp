package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.data.WeatherModel
import com.example.weatherapp.screens.mainCard.MainCard
import com.example.weatherapp.screens.tabLayout.TabLayout
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModel("", "", "", "", "", "", "", ""))
                }
                getData("London", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList)
                }
            }
        }
    }
}

private fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url = "https://weatherapi-com.p.rapidapi.com/forecast.json?q=$city&days=3"
    val queue = Volley.newRequestQueue(context)
    val sRequest = object : StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val list = getWeatherDataByDays(response)
            currentDay.value = list[0]
            daysList.value = list
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

private fun getWeatherDataByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()
    val mainObject = JSONObject(response)
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    val city = mainObject.getJSONObject("location").getString("name")
    val list = ArrayList<WeatherModel>()
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                ""
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")
    )
    return list
}
