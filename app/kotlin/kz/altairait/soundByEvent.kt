package kz.altairait;
import kz.aisuluait.a11yevents.Event;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.feedback.play;
import kz.aisuluait.R;
import kz.aisuluait.focus.checkLabels;
fun soundByEvent(event: Event?) {
event ?: run {
var param: Any = Global.prefs.getString("void_sound", null) ?: return;
play(param);
}
event ?: return;

val eventType = event.eventType;
val node = event.source;
val param: Any? = when (eventType) {
Event.TYPE_ANNOUNCEMENT -> Global.prefs.getString("announcement_sound", null) ?: return;
Event.TYPE_VIEW_LONG_CLICKED -> Global.prefs.getString("long_click_sound", null) ?: R.raw.long_click;
Event.TYPE_VIEW_CLICKED -> Global.prefs.getString("click_sound", null) ?: R.raw.click;
//Event.TYPE_VIEW_SCROLLED -> scroll;
//Event.TYPE_WINDOWS_CHANGED -> windowsChanged;
Event.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> getParamByNode(node);
Event.TYPE_GESTURE_DETECTION_END -> Global.prefs.getString("gesture_end_sound", null) ?: return;
else -> null;
}
if (param != null) {
val sound = if (param is String) {
Global.prefs.getString(param, null) ?: getDefaultSound(node);
} else {
param;
}
play(sound);
}

}
private fun getParamByNode(node: Node?): String {
node ?: return "unknown-sound";
if (!node.isEnabled) {
return "disabled_sound";
}
val className = (node.className ?: "").toString();
if (className == "android.widget.EditText") {
return "input_sound";
}
if (node.isPassword) {
return "password_sound";
}
if (node.isClickable) {
return "clickable_sound";
}
if (node.isLongClickable) {
return "long_clickable_sound";
}
if (node.isFocusable) {
return "focusable_sound";
}
if (checkLabels(node)) {
return "text_sound";
}
if (node.isSelected) {
return "selected_sound";
}
if (node.rangeInfo != null) {
return "slider_sound";
}
return "text_sound";
}

private fun getDefaultSound(node: Node?): Int {
node ?: return 0;
if (!node.isEnabled) {
return R.raw.focus;
}
val className = (node.className ?: "").toString();
if (className == "android.widget.EditText") {
return R.raw.focus;
}
if (node.isPassword) {
return R.raw.focus;
}
if (node.isClickable) {
return R.raw.focus;
}
if (node.isLongClickable) {
return R.raw.focus;
}
if (node.isFocusable) {
return R.raw.focus;
}
if (node.isSelected) {
return R.raw.focus;
}
if (node.rangeInfo != null) {
return R.raw.focus;
}
return R.raw.text;
}

