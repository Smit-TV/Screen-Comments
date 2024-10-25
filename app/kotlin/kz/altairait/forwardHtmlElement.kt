package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.focus.hasAnyClick;
import kz.aisuluait.focus.hasAnyActions;
import kz.aisuluait.focus.checkLabels;
import kz.aisuluait.focus.isHtmlElement;

// Переход к следующему HTML элементу
fun forwardHtmlElement(node: Node, nodeForComparison: Node? = null): Node? {
if (!isHtmlElement(node)) {
return null;
}
try {
val childCount = node.childCount;
if (childCount > 0) {
var foundFocus = node.isAccessibilityFocused || false;
for (childId in 0 until childCount) {
val child = node.getChild(childId) ?: break;
val hasAnyClick = hasAnyClick(child);
val hasAnyActions = hasAnyActions(child);
val checkLabels = checkLabels(node);
if (child.isAccessibilityFocused
|| child.equals(nodeForComparison)) {
foundFocus = true;
continue;
}
if (foundFocus) {
if (checkLabels/*
|| hasAnyClick || hasAnyActions*/) {
return child;
}
}
}
}
return forwardHtmlElement(node.parent ?: return null, node);
}
catch (e: Exception) {
e.printStackTrace();
}
return null;
}
