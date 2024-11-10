package kz.aisuluait;
import com.google.android.marvin.talkback.TalkBackService;
import android.view.accessibility.AccessibilityEvent;
class AppService : TalkBackService() {
override fun onServiceConnected() {
super.onServiceConnected();
}
override fun onInterrupt() {
super.onInterrupt();
}
override fun onDestroy() {
super.onDestroy();
}
override fun onAccessibilityEvent(event: AccessibilityEvent) {
super.onAccessibilityEvent(event);
}
}
