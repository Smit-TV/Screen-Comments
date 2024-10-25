package kz.aisuluait.a11yevents;
import kz.altairait.farwardNode;
import kz.aisuluait.focus.getFocusableNode;
private var lastEventTime = 0L;
fun focused(node: Node?, eventTime: Long) {
node ?: return;
Thread {
try {
// Очередь для фокуса
// Например: На Samsung One UI в папках фокус смещается 3 раза подряд
if (eventTime - 1500 < lastEventTime) {
Thread.sleep(eventTime - lastEventTime);
}
lastEventTime = eventTime;
Global.lastFocusedNode = node;
//if (node.isFocused) {
//node.performAction(Node.ACTION_CLEAR_FOCUS);
//node.performAction(Node.ACTION_CLEAR_SELECTION);
//}
if (!node.isVisibleToUser
|| node.isContentInvalid
|| node.isAccessibilityFocused
|| !node.window.isActive) {
throw Exception();
}
val focusedNode = getFocusableNode(node) ?: throw Exception();
if (node.equals(focusedNode)) {
node.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);
} else if (node.childCount > 0) {
farwardNode(node, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);
}
}
catch (e: Exception) {}
}.start();
}
