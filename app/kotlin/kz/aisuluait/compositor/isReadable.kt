package kz.aisuluait.compositor;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.Action;

// Проверка на
//  ACTION_NEXT_AT_MOVEMENT_GRANULARITY и ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY
fun isReadable(node: NodeCompat): Boolean {
val actions = node.actionList;
if (actions.indexOf(
Action. ACTION_NEXT_AT_MOVEMENT_GRANULARITY) != -1
|| actions.indexOf(
Action. ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY) != -1) {
return true;
}
return false;
}