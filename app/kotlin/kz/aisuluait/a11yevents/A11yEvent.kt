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
a11yFocused(event);
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
Global.readFromNextElementInterrupt = true;
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
Event.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY -> reading(event);
}
Global.lastEvent = event;
getEventLog(event);
}
