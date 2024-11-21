package kz.aisuluait.feedback;
import android.speech.tts.UtteranceProgressListener;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Global;
object TTSUtteranceProgressListener : UtteranceProgressListener() {
override fun onStart(utteranceId: String) {
if (utteranceId == "announcement") {
vibrate(53);
}
}
override fun onDone(utteranceId: String) {
// Чтение завершилось. Можем переходить к следующему элементу если надо
Global.readFromNextElement = false;}
override fun onError(utteranceId: String) {
// Прервем чтение со следующего элемента так как возникла ошибка
Global.readFromNextElementInterrupt = true;
play(R.raw.speech_error);
}
override fun onStop(utteranceId: String, interrupt: Boolean) {
}
}
