package kz.aisuluait.feedback;
import android.speech.tts.UtteranceProgressListener;
import kz.aisuluait.R;
object TTSUtteranceProgressListener : UtteranceProgressListener() {
override fun onStart(utteranceId: String) {
if (utteranceId == "announcement") {
vibrate(53);
}
}
override fun onDone(utteranceId: String) {}
override fun onError(utteranceId: String) {
play(R.raw.speech_error);
}
override fun onStop(utteranceId: String, interrupt: Boolean) {}
}
