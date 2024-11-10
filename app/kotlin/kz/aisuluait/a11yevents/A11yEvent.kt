package kz.aisuluait.a11yevents;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.feedback.*;
import kz.aisuluait.log.getEventLog;
typealias Event = AccessibilityEvent;
fun a11yEvent(event: Event) {
val cxt = Global.cxt;
var node: Node? = event.source;
val eventTime = event.eventTime;
val eventType = event.eventType;
when (eventType) {
Event.TYPE_ANNOUNCEMENT -> {
announcement(event, cxt);
}
Event.TYPE_ASSIST_READING_CONTEXT -> {
Global.tts.speak(
event.toString());
}
Event.TYPE_VIEW_HOVER_ENTER -> {
hoverEnter(node, cxt);
}
Event.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
a11yFocused(node);
}
Event.TYPE_VIEW_CLICKED,
Event.TYPE_VIEW_LONG_CLICKED -> {
anyClicked(node, eventType);
}
Event.TYPE_VIEW_SCROLLED -> {
scroll(event);
}
Event.TYPE_TOUCH_INTERACTION_START -> {
Global.tts.stop();
}
Event.TYPE_TOUCH_INTERACTION_END -> {
Global.lastUniqueId = "";
try {
val node = Global.node ?: return;
if (node.isTextEntryKey == true
|| node.window.type == Window.TYPE_INPUT_METHOD) {
Global.node?.performAction(
Node.ACTION_CLICK);
}
}
catch (e: Exception) {}
}
Event.TYPE_NOTIFICATION_STATE_CHANGED -> {
notificationReader(event, cxt);
}
Event.TYPE_VIEW_FOCUSED -> {
focused(node, eventTime);
}
Event.TYPE_WINDOW_CONTENT_CHANGED -> {
windowContentChanged(event);
}
Event.TYPE_VIEW_SELECTED -> {
selected(node);
}
}
Global.lastEvent = event;
getEventLog(event);
}
