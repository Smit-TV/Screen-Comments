package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.log.getLog;
fun checkNotFocusableNode(node: Node): Boolean {
try {
// Узлы списки не подлежат проверке.
// Все в них является элементом списка,
// даже если это не явное collectionItemInfo
if (isCollection(node)) {
return false;
}
for (childId in 0 until node.childCount) {
val child = node.getChild(childId) ?: return false;
val checkLabels = checkLabels(child);
val hasAnyClick = hasAnyClick(child)
val hasAnyFocus = hasAnyFocus(child);
val isScrollable = isScrollable(child);
// Мы не должны проверять виден ли узел 
// Например: 
// В Expo Go для react-native 
// Есть вкладка 'Settings'
// но, все узлы в ней невидимые
if (/*child.isVisibleToUser
&&*/ !isScrollable
&& !isCollection(child)
&& !hasAnyClick
&& !hasAnyFocus
&& child.childCount == 0
&& checkLabels) {
getLog(child, "scrcchecker");
return true;
} else if (/*child.isVisibleToUser
&&*/ !isScrollable
&& !isCollection(child)
&& !hasAnyFocus
&& !hasAnyClick
&& child.childCount > 0
&& checkNotFocusableNode(child)) {
//getLog(child, "scrcchecker");
return true;
}
}
return false;
}
catch (e: IllegalStateException) {
return false;
}
}
