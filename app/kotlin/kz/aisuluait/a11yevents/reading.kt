package kz.aisuluait.a11yevents;
import kz.aisuluait.compositor.NodeProperties;

// Обрабатывает Event#TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY
fun reading(event: Event) {
val node = event.source ?: return;
val text = NodeProperties(NodeCompat.wrap(node), event, Global.cxt).contentDescription;
val textToSpeak = text.substring(event.fromIndex, event.toIndex);
Global.tts.speak(textToSpeak);

}
