package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;

fun hasAnyClick(node: Node): Boolean {
val actionList = node.actionList;
return (node.isClickable
|| node.isLongClickable
|| actionList.indexOf(
ActionInfo.ACTION_CLICK
) != -1
|| actionList.indexOf(
ActionInfo.ACTION_LONG_CLICK
) != -1
);
}
