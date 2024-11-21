package kz.aisuluait.feedback;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.os.VibratorManager;
import android.os.VibrationAttributes;
import android.os.CombinedVibration;
import android.annotation.TargetApi;
import androidx.annotation.RequiresApi;
import kz.aisuluait.a11yevents.Global;
private var vibrator = Global.cxt.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator;
private val hasVibrator = vibrator.hasVibrator();
@RequiresApi(31)
private val vibratorManager = Global.cxt.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager;
private val vibrationAttributes = (VibrationAttributes.Builder()
.setUsage(VibrationAttributes.USAGE_ACCESSIBILITY)
.build()
);
fun vibrate(obj: Any, cxt: Context? = Global.cxt) {
Thread {
if (hasVibrator) {
if ( Build.VERSION.SDK_INT >= 31) {
vibratorManager.cancel();
var effect = obj;
if (obj is Long
|| obj is Int
|| obj is Float) {
effect = VibrationEffect.createWaveform(
    longArrayOf(67),
    intArrayOf(250),
    -1
)
}
if (effect is VibrationEffect) {
val combinedVibration = CombinedVibration.createParallel(effect);
vibratorManager.vibrate(combinedVibration, vibrationAttributes)

}
} else {
if (obj is Number) {
vibrator.vibrate(obj.toLong());
} else {
vibrator.vibrate(56);
}
}
}
}.start();
}
