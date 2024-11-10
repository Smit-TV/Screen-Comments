package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
fun isHtmlElement(node: Node): Boolean {
val actions = node.actionList ?: mutableListOf<ActionInfo?>();
return (
actions.indexOf(
ActionInfo.ACTION_NEXT_HTML_ELEMENT
) != -1
|| actions.indexOf(
ActionInfo.ACTION_PREVIOUS_HTML_ELEMENT
) != -1
);
}
