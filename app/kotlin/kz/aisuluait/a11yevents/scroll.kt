package kz.aisuluait.a11yevents;
import kz.aisuluait.feedback.vibrate;
import kz.altairait.farwardNode;
fun scroll(event: Event) {
return;
if (Global.scrollInitByGesture) {
Global.scrollInitByGesture = false;
val node = event.source ?: return;
farwardNode(node, false, null, 0, true)?.performAction(Node.ACTION_ACCESSIBILITY_FOCUS);
}
}
