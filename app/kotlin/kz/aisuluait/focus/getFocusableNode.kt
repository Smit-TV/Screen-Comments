package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
fun getFocusableNode(node: Node): Node? {
try {
//return node;
if (node.className ?: "" == "android.webkit.WebView") {
return null;
}
val isScrollable = isScrollable(node);
val hasAnyClick = hasAnyClick(node);
val hasAnyFocus = hasAnyFocus(node);
val hasAnyActions = hasAnyActions(node);
val checkLabels = checkLabels(node);

if (!isHtmlElement(node)) {
val parent = getFocusableParent(node);
val checker = checkNotFocusableNode(node);
if (node.childCount > 0
&& !checker
&& !checkLabels
|| !node.isVisibleToUser) {
return parent;
}
if (isCollection(node)) {
if (!hasAnyClick
&& !hasAnyActions) {
return null;
}
return node;
}
node.collectionItemInfo?.let {
if (!hasAnyClick
&& !hasAnyFocus
&& !checkLabels
&& !checker) {
return null;
}
return node;
}
if (isScrollable
&& !hasAnyActions
&& !hasAnyClick
&& !checkLabels) {
return null;
}
val possibleParent = node.parent;
// Не явные элементы списков или scrollview
if (possibleParent != null && isCollection(possibleParent) == true
|| isScrollable(possibleParent)) {
if (checker
|| checkLabels) {
return node;
}
}
if (checkLabels && parent == null
|| hasAnyClick
|| hasAnyFocus) {
return node;
}
return parent;
} else {
if (checkLabels || hasAnyClick || hasAnyActions) {
return node;
}
}
}
catch (e: Exception) {
e.printStackTrace();
}
return null;
}
