package kz.aisuluait.feedback;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.getParam;
class SoundPoolManager {
val cxt = Global.cxt;
var soundId = 0;
var loaded = false;
val audioAttributes = AudioAttributes.Builder()
.setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
.build();
val soundPool = SoundPool.Builder()
.setAudioAttributes(audioAttributes)
.setMaxStreams(2)
.build();
fun loadSound(file: Any): Int? {
try {
soundPool.setOnLoadCompleteListener {
_, sampleId, status ->
loaded = status == 0;
soundId = sampleId;
if (loaded) {
soundPool.play(soundId, 
Global.prefs.getFloat("left_volume", 0.6f), 
Global.prefs.getFloat("right_volume", 0.6f), 
1, 0, 1.0030f);
}
}
if (file is String) {
soundId = soundPool.load(file, 1) ?: 0;
} else if (file is Int) {
soundId = soundPool.load(cxt, file, 1);
} else {
return null;
}
}
catch (e: Exception) {}
return soundId;
}
fun playSound(localSoundId: Int) {
soundPool.play(localSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
}
}
