package kz.aisuluait.feedback;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;
import kz.aisuluait.a11yevents.Global;
private val soundPoolManager = SoundPoolManager();
private val soundIds = HashMap<String, Int?>();
fun play(soundResource: Any) {
val soundId = soundIds["$soundResource"];
if (soundId == null) {
soundIds["$soundResource"] = soundPoolManager.loadSound(soundResource);
} else if (soundId != null) {
soundPoolManager.playSound(soundId);
}

}


