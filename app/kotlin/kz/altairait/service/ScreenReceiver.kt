package kz.altairait.service;
import android.os.PowerManager;
import android.os.BatteryManager;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import kz.aisuluait.R;
import kz.aisuluait.feedback.vibrate;
import kz.aisuluait.feedback.play;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.functions.getDate;
// Раньше мы не знали об возможностях BroadCastReceiver и думали что он только для одного действия.

class ScreenReceiver(private val cxt: Context) : BroadcastReceiver() {
private val powerManager = cxt.getSystemService(Context.POWER_SERVICE) as PowerManager;
override fun onReceive(cxt: Context, intent: Intent) {
val batteryLevel = Global.batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

when (intent?.action ?: return) {
Intent.ACTION_POWER_CONNECTED,
Intent.ACTION_POWER_DISCONNECTED -> {
Global.tts.speak(
"${Global.cxt.getString(R.string.battery_level)} $batteryLevel%");
}
Intent.ACTION_SCREEN_OFF -> {
// Чтобы при включении экрана пользователь узнавал название приложения
Global.window = null;
// Если музыка играет то, мы не должны мешать прослушиванию без разрешения.
if (!Global.audioManager.isMusicActive
&& Global.prefs.getInt("notify_about_screen_off_set", 1) > 0) {
play(
Global.prefs.getString("screen_off_sound", null) ?: R.raw.windows_changed);
Global.tts.speak(cxt.getString(R.string.device_locked));
}
vibrate("screen_off_vibration");
}
Intent.ACTION_SCREEN_ON -> {
val order = Global.prefs.getInt("order_when_screen_on_set", 1);
if (order == 0) return;
Global.tts.speak(
if (order == 1) "$batteryLevel%\n${getDate()}" else "${getDate()}\n$batteryLevel%", 1); 
}
}
}
fun screenWorking(): Boolean {
return powerManager.isInteractive;
}
}
