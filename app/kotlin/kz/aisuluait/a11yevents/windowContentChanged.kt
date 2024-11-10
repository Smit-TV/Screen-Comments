package kz.aisuluait.a11yevents;
import kz.aisuluait.R;
fun windowContentChanged(event: Event) {
val contentChanges = event.contentChangeTypes;
if (contentChanges and Event.CONTENT_CHANGE_TYPE_DRAG_CANCELLED != 0) {
speak(Global.cxt.getString(R.string.drag_cancelled));
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_DRAG_DROPPED != 0) {
speak(Global.cxt.getString(R.string.drag_stopped));
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_DRAG_STARTED != 0) {
speak(Global.cxt.getString(R.string.drag_started));
}
val node = event.source ?: return;
var msg: CharSequence = "";
var interrupt = false;
// Ошибки, уведомления об ошибках
if (Event.CONTENT_CHANGE_TYPE_ERROR and contentChanges != 0) {
msg = node.error ?: "";
if (!msg.isNotBlank()) {
Global.tts.speak(msg, 1);
}
}
// Изменения, которые требуют чтобы узел являлся текущим сфокусируемым или его потомком
if (!node.isAccessibilityFocused) {
var parent: Node? = node;
while (parent != null) {
// На случай если родителя нет
parent = parent.parent ?: return;
if (parent.isAccessibilityFocused) {
parent = null;
break;
}
}
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_STATE_DESCRIPTION != 0) {
msg = node.stateDescription ?: "";
speak(msg, 1, "state_description_changed");
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION != 0) {
msg = node.contentDescription ?: "";
if (msg?.isNotBlank() == true) {
interrupt = true;
speak(msg);
}
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_TEXT != 0) {
if (node.contentDescription?.isBlank() == true) {
interrupt = true;
}
msg = node.text ?: "";
if (!interrupt) {
speak(msg);
}
}
if (contentChanges and Event.CONTENT_CHANGE_TYPE_ENABLED != 0) {
val isDisable = node.isEnabled == false;
if (isDisable) {
speak(Global.cxt.getString(R.string.state_disable), 1, "node_disabled");
}
}
}
// Очереди по умолчанию нет
private fun speak(text: CharSequence, speechQueue: Int = 0, speechId: String? = null) {
if (text.isNotBlank()) {
Global.tts.speak(text, speechQueue, speechId);
}
}
