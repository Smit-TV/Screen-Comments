package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;

// Отличается от hasAnyFocus только отсутствием isFocusable.
fun hasAnyActions(node: Node): Boolean {
val actionList = node.actionList ?: mutableListOf<ActionInfo?>();
return (
node.isScreenReaderFocusable
|| node.isDismissable
|| node.canOpenPopup()
|| node.rangeInfo != null
|| !node.isEnabled && node.childCount > 0
|| actionList.indexOf(
ActionInfo.ACTION_EXPAND
) != -1
|| actionList.indexOf(
ActionInfo.ACTION_COLLAPSE
) != -1
);
}
