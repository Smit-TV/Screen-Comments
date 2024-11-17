package kz.aisuluait.a11yevents;
import kz.altairait.farwardNode;
import kz.aisuluait.focus.getFocusableNode;
fun selected(node: Node?) {
node ?: return;
return;/*
if (!node.isVisibleToUser
|| !node.window.isActive) {
return;
}
if (node.isSelected
|| node.isAccessibilityFocused) {
a11yFocused(node);
return;
}
val focusedNode = getFocusableNode(node);
if (node.equals(focusedNode)) {
node.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);
} else if (node.childCount > 0) {
farwardNode(node, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);
}*/
}
