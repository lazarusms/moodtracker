package com.example.moodapp.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodapp.R
import com.example.moodapp.database.repository.MoodRepository
import com.example.moodapp.model.Mood
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
    fun MoodAppScreen(navController: NavController) {
        val context = LocalContext.current
        var showResultCard by remember { mutableStateOf(false) }
        val moodFocusRequester = FocusRequester()
        val focusManager = LocalFocusManager.current
        var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        var mood by remember { mutableStateOf("") }
        val moodRepository = MoodRepository(context)


        Scaffold(
            bottomBar = {
                CustomApp(navController)
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(colorResource(id = R.color.azul))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emotion),
                            contentDescription = "logo",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(top = 16.dp)
                        )
                        Text(
                            text = "MoodTracker",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .offset(y = (30).dp)
                                .fillMaxWidth(),
                            colors = CardDefaults
                                .cardColors(containerColor = Color(0xFAD2D4E2)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    vertical = 24.dp,
                                    horizontal = 32.dp
                                )
                            ) {
                                Text(
                                    text = "Registre seu humor",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.azul),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                TextInput(
                                    InputType.Date(currentDate),
                                    { currentDate = it },
                                    keyboardActions = KeyboardActions(
                                        onNext = { moodFocusRequester.requestFocus() }
                                    ))

                                Spacer(modifier = Modifier.height(32.dp))

                                TextInput(
                                    InputType.Mood,
                                    { mood = it },
                                    keyboardActions = KeyboardActions(onDone = {
                                        focusManager.clearFocus()
                                    }),
                                    focusRequester = moodFocusRequester
                                )


                                Spacer(modifier = Modifier.height(32.dp))

                                Button(
                                    onClick = {
                                        val mood2 = Mood (
                                            mood = mood,
                                            date = currentDate
                                        )


                                        if (mood.isNotBlank()) {
                                            moodRepository.salvar(mood2)
                                            showResultCard = true
                                        } else {
                                            showResultCard = false;
                                            Toast.makeText(
                                                context,
                                                "Por favor, insira seu humor.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.azul
                                        )
                                    )
                                ) {
                                    Text(
                                        text = "Registrar",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }

                            }
                        }
                        if (showResultCard) {
                            ResultCard()
                        }
                    }
                }
            }
        }
    }

