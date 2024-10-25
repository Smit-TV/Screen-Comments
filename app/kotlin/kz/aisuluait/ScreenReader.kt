package kz.aisuluait;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.accessibilityservice.AccessibilityService;
/*import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import kz.aisuluait.R;
import kz.aisuluait.feedback.*;
import kz.aisuluait.a11yevents.A11yEvent;/*
import kz.altairait.gestures.gestureEvent;
import kz.altairait.service.ScreenReceiver;
class ScreenReader : AccessibilityService() {
private lateinit var tts: TTS;
private lateinit var cxt: Context;
private lateinit var a11yEvent: A11yEvent;
private lateinit var screenReceiver: ScreenReceiver;
override fun onServiceConnected() {
super.onServiceConnected();
cxt = applicationContext;
tts = TTS(cxt, cxt.getString(R.string.service_on));
vibrate(34, this);
val info = serviceInfo;
info.flags = (info.flags
or AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE
or AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS);
serviceInfo = info;
a11yEvent = A11yEvent(cxt);
screenReceiver = ScreenReceiver(cxt);
val filter = IntentFilter(Intent.ACTION_SCREEN_OFF);
cxt.registerReceiver(screenReceiver, filter);
}
override fun onDestroy() {
super.onDestroy();
cxt?.unregisterReceiver(screenReceiver);
tts.speak(cxt.getString(R.string.service_off), tts.QUEUE_FLUSH);
}
override fun onInterrupt() {
Log.d("scrc", "onInterrupt()");
}
override fun onKeyEvent(keyEvent: KeyEvent): Boolean {
return false;
}
override fun onGesture(gesture: Int): Boolean {
gestureEvent(gesture);
vibrate(32, cxt);
return true;
}
override fun onAccessibilityEvent(event: AccessibilityEvent) {
a11yEvent.run(event);
}
}
*/