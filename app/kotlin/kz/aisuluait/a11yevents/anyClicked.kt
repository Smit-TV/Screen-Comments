package kz.aisuluait.a11yevents;
import android.os.VibrationEffect;
import kz.aisuluait.feedback.vibrate;
fun anyClicked(node: Node?, eventType: Int) {
node ?: return;
if (eventType == Event.TYPE_VIEW_CLICKED) {
// Не будем мешать вибрацией при печатании
if (node.isTextEntryKey) {
return;
}
vibrate(5);
} else {
vibrate(
VibrationEffect.createWaveform(longArrayOf(108),
intArrayOf(150), -1));
}
//Global.lastFocusedNode?.performAction(
//Node.ACTION_CLEAR_FOCUS);
if (node.isContentInvalid) return;
//node.performAction(Node.ACTION_SET_SELECTION);

}
