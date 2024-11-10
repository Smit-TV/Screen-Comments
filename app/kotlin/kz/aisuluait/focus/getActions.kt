package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
fun getActions(node: Node): HashMap<Int, Boolean> {
val actions = HashMap<Int, Boolean>();
val actionList = node.actionList;
//actions[ActionInfo.ACTION_FOCUS.id] = actionList[ActionInfo.ACTION_FOCUS.id] ?: false;
actions[ActionInfo.ACTION_SHOW_ON_SCREEN.id] = node.isVisibleToUser;
actions[ActionInfo.ACTION_CLICK.id] = node.isClickable
actions[ActionInfo.ACTION_LONG_CLICK.id] = node.isLongClickable;
actions[ActionInfo.ACTION_FOCUS.id] = node.isFocusable;
for (action in node.actionList) {
actions[action.id] = true;
if (action.id == ActionInfo.ACTION_CLICK.id
|| action.id == ActionInfo.ACTION_LONG_CLICK.id
|| action.id == ActionInfo.ACTION_FOCUS.id) {
return actions;
}
}
return actions;
}
