package com.example.pomodoro

import TimerViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


class MainActivity : ComponentActivity() {
    val timerViewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    TimerScreen(
                        timeLeft = formatTime(timerViewModel.timeLeft.value),
                        onStartClick = { timerViewModel.startTimer() },
                        onPauseClick = { timerViewModel.pauseTimer() },
                        onResetClick = { timerViewModel.resetTimer() },
                        onSetClick = { timerViewModel.setTimer() }
                    )
                    }
                }
            }
        }

@SuppressLint("DefaultLocale")
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
    @Composable
    fun TimerScreen(
        timeLeft: String,
        onStartClick: () -> Unit,
        onPauseClick: () -> Unit,
        onResetClick: () -> Unit,
        onSetClick: () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = timeLeft, fontSize = 48.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = onStartClick) { Text("Start") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onPauseClick) { Text("Pause") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onResetClick) { Text("Reset") }
            }
            Row {
                Button(onClick = onSetClick){
                var showDialog by remember { mutableStateOf(false)}
                if (showDialog) {
                    ShowSetTimerDialog(onDismiss = { showDialog = false })
                }
                    Text("Set Timer")
                }
            }
        }
    }

@Composable
    fun ShowSetTimerDialog(onDismiss: () -> Unit) {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                Column(Modifier.background(Color.White)) {
                    Text(
                        text = "Wähle einen Timer",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f)
                    ) {
                    }
                    var timeList = listOf(5, 10, 15, 20, 25)
                    for (i in 1..timeList.size) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(i.toString())
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { onDismiss() }) {
                        Text("Done")
                    }
                }
            })
    }}