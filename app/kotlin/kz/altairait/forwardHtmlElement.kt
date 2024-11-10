package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.focus.hasAnyClick;
import kz.aisuluait.focus.hasAnyActions;
import kz.aisuluait.focus.checkLabels;
import kz.aisuluait.focus.isHtmlElement;

// Переход к следующему HTML элементу
fun forwardHtmlElement(node: Node, isChild: Boolean = false, nodeForComparison: Node? = null): Node? {
if (!isHtmlElement(node)) {
return null;
}
try {
val childCount = node.childCount;
val parentHasAnyClick = hasAnyClick(node);
if (childCount > 0) {
var foundFocus = node.isAccessibilityFocused || isChild;
for (childId in 0 until childCount) {
val child = node.getChild(childId) ?: continue;
val hasAnyClick = hasAnyClick(child);
val hasAnyActions = hasAnyActions(child);
val checkLabels = checkLabels(node);
if (!foundFocus
&& (child.isAccessibilityFocused
|| child.equals(nodeForComparison))) {
foundFocus = true;
continue;
}
if (foundFocus) {
if (child.childCount == 0) {
if (checkLabels && !parentHasAnyClick 
|| hasAnyClick || hasAnyActions) {
if (child.isVisibleToUser) {
return child;
}
}
}
if (child.childCount > 0) {
if (hasAnyClick || hasAnyActions
 || checkLabels && !parentHasAnyClick) {
if (child.isVisibleToUser) {
return child;
}
}
forwardHtmlElement(child, true)?.let {
return it;
}
}
}
}
}
return forwardHtmlElement(node.parent ?: return null, false, node);
}
catch (e: Exception) {
e.printStackTrace();
}
return null;
}
