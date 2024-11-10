package kz.altairait;
import kz.aisuluait.a11yevents.Node;
fun getActions(node: Node): HashMap<Int, String?> {
val actions = HashMap<Int, String?>();
val actionList = node.actionList;
for (action in actionList) {
val actionLabel: String? = if (action.label != null) {
action.label.toString();
} else { null; }
actions[action.id] = actionLabel;
}
return actions;
}