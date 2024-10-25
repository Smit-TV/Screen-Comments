package kz.aisuluait.log;
import android.util.Log;
import kz.aisuluait.a11yevents.Window;
fun getWindowLog(window: Window) {
Thread {
val windowInfo = "title $window.title type $window.type id $window.id";
Log.d("scrcwindow", windowInfo);
}.start();
}