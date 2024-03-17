package com.example.moodapp.util

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodapp.R
import com.example.moodapp.database.repository.MoodRepository
import com.example.moodapp.model.Mood

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoodHistoryScreen(navController: NavController) {
    val context = LocalContext.current
    val moodRepository = MoodRepository(context)
    val moods = moodRepository.listarMoods()

    Scaffold(
        bottomBar = {
            CustomApp(navController)
        },
        topBar = { MoodTrackerCard() }
    ) {
        Column(
            modifier = Modifier.offset(y = 160.dp)
        ) {
            MoodList(moods)
        }
    }
}
@Composable
fun MoodList(moods: List<Mood>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {
        for (mood in moods){
            MoodCard(mood)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun MoodCard(mood: Mood) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(
                id = R.color.azul
            )
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dial),
                contentDescription = "logo",
                modifier = Modifier
                    .size(72.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Mood: ${mood.mood}",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Data: ${mood.date}",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}