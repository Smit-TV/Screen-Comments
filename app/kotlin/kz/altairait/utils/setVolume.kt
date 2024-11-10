package kz.altairait.utils;
import android.os.VibrationEffect;
import android.content.Context;
import android.media.AudioManager;
import kz.aisuluait.R;
import kz.aisuluait.feedback.vibrate;
import kz.aisuluait.feedback.play;
import kz.aisuluait.a11yevents.Global;
private var audioManager: AudioManager? = null;
fun setVolume(volumeEvent: String, cxt: Context? = null, stream: Int = AudioManager.STREAM_ACCESSIBILITY): Boolean? {
cxt ?: return false;
audioManager ?: run {
audioManager = cxt.getSystemService(Context.AUDIO_SERVICE) as AudioManager;
}
val currentVolume = audioManager?.getStreamVolume(stream) ?: return false;
val maxVolume = audioManager?.getStreamMaxVolume(stream) ?: return false;
val newVolume = if (volumeEvent == "volume-up") currentVolume + 1 else currentVolume - 1;
if (newVolume <= maxVolume
&& newVolume > 0) {
audioManager?.setStreamVolume(stream, newVolume, 0);
return true;
} else {
play(
Global.prefs.getString(
"end_sound", null) ?: R.raw.end
);
vibrate(VibrationEffect.createWaveform(
longArrayOf(50), intArrayOf(250), -1));
return null;
}
}
