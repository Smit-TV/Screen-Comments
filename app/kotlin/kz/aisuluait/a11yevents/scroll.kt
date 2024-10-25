package kz.aisuluait.a11yevents;
import kz.aisuluait.feedback.vibrate;
fun scroll(event: Event) {
return;
val scrollX = event.scrollDeltaX;
val scrollY = event.scrollDeltaY;
val packageName = event.packageName ?: "";
val maxX = event.maxScrollX;
val maxY = event.maxScrollY;
if (scrollY > maxY - 350) {
vibrate(57);
}
}
