package kz.aisuluait.a11yevents;
import android.view.accessibility.AccessibilityWindowInfo;
import kz.aisuluait.feedback.vibrate;
import kz.aisuluait.functions.nodeByDirection;
import kz.aisuluait.log.getLog;
import kz.aisuluait.R;
import kz.altairait.farwardNode;
typealias Window = AccessibilityWindowInfo;
private var lastEventTime = 0L;
fun windowStateChanged(event: Event) {
if (Global.screenReceiver.screenWorking()) {
val contentChangeTypes = event.contentChangeTypes;
if (contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_DISAPPEARED != 0) {
return;
}
if (contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_APPEARED != 0
|| contentChangeTypes == 0
|| contentChangeTypes and Event.CONTENT_CHANGE_TYPE_PANE_TITLE != 0
) {
val window = event.source?.window ?: return;
val lastWTitle = (Global.window?.title ?: "").toString();
val title = (event.text?.getOrNull(0) ?: return).toString();
if (window.equals(Global.window)
 && event.eventTime - 3650 < lastEventTime
|| title.isEmpty()) {
return;
}
lastEventTime = event.eventTime;
Global.window = window;
if (!window.isActive
&& !window.isFocused
&& !window.isAccessibilityFocused) {
return;
}
val rootNode = window.root;
if (rootNode?.isVisibleToUser == false) {
return;
}
rootNode?.refresh();
getLog(rootNode, "scrcrootnode");
rootNode.performAction(
Node.ACTION_CLEAR_FOCUS);
rootNode?.recycle();
Global.node = farwardNode(rootNode, true) ?: rootNode.parent ?: rootNode.getChild(0) ?: rootNode ?: return;
Global.node?.performAction(Node.ACTION_ACCESSIBILITY_FOCUS);
Global.tts.speak(
title.toString(),
1);
}
}
}
