package kz.aisuluait.a11yevents;
import android.content.Context;
import kz.aisuluait.compositor.compositor;
import kz.aisuluait.focus.getFocusableNode;
import kz.aisuluait.feedback.*;
import kz.aisuluait.log.getLog;
import kz.altairait.utils.getUniqueId;
interface GetFocusableNode {
fun run(node: NodeCompat);
}
private var lastFocusTime = 0L;
fun a11yFocused(event: Event) {
val node = event.source;
node ?: return;
// Защита от View  которые отправляют более 1 TYPE_VIEW_ACCESSIBILITY_FOCUSED на performAction(ACTION_ACCESSIBILITY_FOCUS)
val currentTime = System.currentTimeMillis();
if (currentTime - 500 < lastFocusTime
&& node.equals(Global.node)) {
return;
}
val textToSpeak = compositor(node, event, Global.cxt);
Global.tts.speak(textToSpeak);
// Не будем мешать печатать
if (!node.isTextEntryKey) {
vibrate(31);
}
lastFocusTime = currentTime;
Global.currentUniqueId = getUniqueId(node);
Global.lastUniqueId = Global.currentUniqueId;
val window = node.window;
if (window.type != Window.TYPE_ACCESSIBILITY_OVERLAY
&& node.packageName != Global.cxt.packageName) {
Global.node = node;
}
getLog(node);
getLog(node.parent ?: return, "scrcparent");
/*getLog(node.parent?.parent ?: return);*/
}

