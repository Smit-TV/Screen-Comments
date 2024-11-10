package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.log.getLog;
// Ищет узел доступный для фокуса
fun getFirstFocusableNode(node: Node): Node? {
try {
for (childId in 0 until node.childCount) {
val child = node.getChild(childId) ?: return null;
val checker = checkNotFocusableNode(child);
val checkLabels = checkLabels(child);
val hasAnyClick = hasAnyClick(child)
val hasAnyFocus = hasAnyFocus(child);
val isScrollable = isScrollable(child);
val parent = getFocusableParent(child);
val isCollection = isCollection(child);
val isCollectionItem = isCollectionItem(child);
val isScrollViewItem = isScrollViewItem(node);
// Мы не можем исключить узлы с 0 детей
if (isScrollable || isCollection) {
getFirstFocusableNode(child)?.let {
return it;
}
continue;
}
if (child.childCount > 0) {
if (child.isVisibleToUser
&& (checker || checkLabels) 
&& (hasAnyClick
|| hasAnyFocus
|| isScrollViewItem || isCollectionItem)) {
return child;
}
getFirstFocusableNode(child)?.let {
return it;
}
}
if (child.childCount == 0
&& child.isVisibleToUser) {
if (hasAnyClick || hasAnyFocus
|| (isCollectionItem || isScrollViewItem) && (checkLabels || hasAnyClick || hasAnyFocus)
|| checkLabels && parent == null) {
return child;
}
}
}
}
catch (e: Exception) {}
return null;
}
