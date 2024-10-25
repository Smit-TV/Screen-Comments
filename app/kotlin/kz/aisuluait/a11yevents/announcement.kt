package kz.aisuluait.a11yevents;
import android.content.Context;
import kz.aisuluait.feedback.TTS;
private var lastTime = 0L;
private var lastText = "";
fun announcement(event: Event, cxt: Context) {
try {
val eventTime = event.eventTime;
val eventText = event.text;
if (lastTime < eventTime - 250
|| eventText.toString() != lastText) {
if (!eventText.isEmpty()) {
lastTime = eventTime;
lastText = eventText.toString();
val text = eventText.getOrNull(0);
if (text?.isEmpty() == false) {
Global.tts?.speak((text ?: "").toString(), 1);
}

}
}
}
catch (e: Exception) {}
}
