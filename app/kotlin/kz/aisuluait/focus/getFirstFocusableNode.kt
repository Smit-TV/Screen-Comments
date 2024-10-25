package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.log.getLog;
fun getFirstFocusableNode(node: Node): Node? {
try {
for (childId in 0 until node.childCount) {
val child = node.getChild(childId) ?: return null;
val checkLabels = checkLabels(child);
val actions = getActions(child);
if ((child.isVisibleToUser || actions[ActionInfo.ACTION_SHOW_ON_SCREEN.id] == true)
&& child.isImportantForAccessibility
&& (child.isClickable || actions[ActionInfo.ACTION_CLICK.id] == true
|| child.isLongClickable || actions[ActionInfo.ACTION_LONG_CLICK.id] == true
|| child.isFocusable || actions[ActionInfo.ACTION_FOCUS.id] == true
|| child.isScreenReaderFocusable)
&& checkLabels) {
getLog(child, "scrcchecker");
return child;
} else if ((child.isVisibleToUser || actions[ActionInfo.ACTION_SHOW_ON_SCREEN.id] == true)
&& child.isImportantForAccessibility
&& (child.isClickable || actions[ActionInfo.ACTION_CLICK.id] == true
|| child.isLongClickable || actions[ActionInfo.ACTION_LONG_CLICK.id] == true
|| child.isFocusable || actions[ActionInfo.ACTION_FOCUS.id] == true
|| child.isScreenReaderFocusable)
&& child.childCount > 0) {
getLog(child, "scrcchecker");
val checker = getFirstFocusableNode(child);
if (checker != null) {
return checker;
}
}
}
return null;
}
catch (e: IllegalStateException) {
return null;
}
}
