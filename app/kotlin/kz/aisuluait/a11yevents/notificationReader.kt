package kz.aisuluait.a11yevents;
import android.app.Notification;
import android.content.Context;
import kz.altairait.utils.getAppNameByPackage;
import kz.altairait.service.ScreenReceiver;
fun notificationReader(event: Event, cxt: Context) {
try {
val className = (event.className ?: "android.widget.Toast").toString();
if (className != "android.widget.Toast") {
val pref = true;
val readNotificationsWhenScreenOff = /*prefs.getBoolean("read_notifications_when_screen_off", */true//);

if (pref) {
val notification = event.parcelableData as Notification;
if (notification != null) {
val title = (notification.extras.getCharSequence(Notification.EXTRA_TITLE) ?: "").toString();
val mess = (notification.extras.getCharSequence(Notification.EXTRA_TEXT) ?: "").toString();
val packageName = (event.packageName ?: "").toString();
val appName = getAppNameByPackage(cxt, packageName);
val text = "$appName $title $mess";
if (readNotificationsWhenScreenOff == false && Global.screenReceiver?.screenWorking() == false) {
return;
}
Global.tts?.speak(text, 1);
return;
}
}
}
val text = (event.text ?: "").toString();
Global.tts?.speak(text, 1);
}
catch (e: Exception) {}
}
