
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
    var minutes = 25
    var timeLeft = mutableStateOf(minutes * 60) // Zeit in Sekunden (25 Minuten)
    private var job: Job? = null


    fun startTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (timeLeft.value > 0) {
                delay(1000) // Warte eine Sekunde
                timeLeft.value -= 1 // Verringere die Zeit um eine Sekunde
            }
            // Timer ist abgelaufen, hier kannst du eine Benachrichtigung anzeigen oder etwas anderes tun
        }
    }

    fun pauseTimer() {
        job?.cancel()
    }

    fun resetTimer() {
        job?.cancel()
        timeLeft.value = 25 * 60 // Zurücksetzen auf 25 Minuten
    }
    fun setTimer(){
        timeLeft.value = minutes * 60
    }

}