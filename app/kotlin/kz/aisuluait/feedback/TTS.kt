package kz.aisuluait.feedback
import android.os.Build;

import android.content.Context
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech
import android.util.Log;
import java.util.Locale

class TTS(private val ctx: Context,
private val initText: String?) : TextToSpeech.OnInitListener {
private var maxSpeechLength: Int = TextToSpeech.getMaxSpeechInputLength();
val audioAttributes =     AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
.setAllowedCapturePolicy(AudioAttributes.ALLOW_CAPTURE_BY_SYSTEM)
        .build()
    var currentTTSService: String = "com.android.system.tts"
val QUEUE_FLUSH = TextToSpeech.QUEUE_FLUSH;
val QUEUE_ADD = TextToSpeech.QUEUE_ADD;
    private var tts: TextToSpeech = TextToSpeech(ctx, this);

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                tts.language = Locale.ENGLISH
            }
        }
if (initText != null) {
speak(initText, TextToSpeech.QUEUE_ADD);
}
tts.setOnUtteranceProgressListener(TTSUtteranceProgressListener);
    }

    fun speak(text: CharSequence, queue: Int = QUEUE_FLUSH, speechId: String? = null) {
if (text.isEmpty()) {
return;
}
if (text.length == 1) {
val symbol: Char = text[0];
if (symbol.isUpperCase()) {
tts.setPitch(1.5f);
}
} else {
tts.setPitch(1.0f);
}

tts.setAudioAttributes(audioAttributes);
            val currentEngine = tts.defaultEngine
            if (currentEngine != currentTTSService) {
                currentTTSService = currentEngine
                tts.setEngineByPackageName(currentEngine)
            }
var utteranceId = "announcement";
//tts.playSilentUtterance(200, QUEUE_ADD, "simple");

            if (queue == TextToSpeech.QUEUE_FLUSH) {
                stop()
utteranceId = "simple";
//tts.playSilentUtterance(3, QUEUE_FLUSH, "simple");

            }
utteranceId = speechId ?: utteranceId;
val subStrs = text.chunked(maxSpeechLength);
for (k in 0 until subStrs.size) {
            tts.speak(subStrs[k].trim(), QUEUE_ADD, null, utteranceId)
Log.d("scrctts", subStrs[k]);
}
    }

    fun stop() {
        tts.stop()
    }
fun isSpeaking(): Boolean {
return tts.isSpeaking();
}

    fun shutdown() {
        tts.shutdown()
    }
}
