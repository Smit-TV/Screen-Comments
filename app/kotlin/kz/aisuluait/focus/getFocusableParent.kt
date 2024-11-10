package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Window;
import kz.aisuluait.log.getLog;
fun getFocusableParent(node: Node): Node? {
try {
val parent = node.parent ?: return null;
//return parent
//return getPreferredNode(parent);
val className = (parent.className ?: "").toString();
val checkLabels = checkLabels(parent);
val hasAnyClick = hasAnyClick(parent);
val hasAnyFocus = hasAnyFocus(parent);
val checker = checkNotFocusableNode(parent);
val grandParent = parent.parent;
val grandParentClassName = (grandParent?.className ?: "").toString();
val isScrollable = isScrollable(parent);
if (checker && !isScrollable(parent)
&& grandParent != null && (isCollection(grandParent) == true
|| isScrollable(grandParent))) {
return parent;
}
if ((hasAnyClick
|| hasAnyFocus
|| isScrollable && !checkLabels)
&& !isCollection(parent)
&& (checker
|| checkLabels)) {
if (isScrollable
&& !checkLabels) {
return null;
}
return parent;
}
return getFocusableParent(parent);
}
catch (e: IllegalStateException) {
return null;
}
}
