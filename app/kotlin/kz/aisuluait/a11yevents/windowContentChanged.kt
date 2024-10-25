package kz.aisuluait.a11yevents;
import kz.aisuluait.feedback.vibrate;
private var lastText = "";
fun windowContentChanged(event: Event) {
/*Global.tts.speak(
(event.contentDescription ?: event.text ?: event.source?.paneTitle ?: event.source?.containerTitle)?.toString() ?: "", 1);*/
return;
val node = event.source ?: return;
val text = "${event.text}${event.contentDescription}".trim();
if (text == lastText) {
return;
}
lastText = text;
Global.node?.refresh();
val comparison = Global.node?.equals(node);
if (comparison == true) {
a11yFocused(node);
}
}
