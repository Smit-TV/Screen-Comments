package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.focus.isScrollable;
fun scrollInterpretation(node: Node, direction: Boolean): Node? {
//return null
if (!isScrollable(node)) {
throw IllegalArgumentException("Узел не является прокручиваемым. Ошибка в farwardNode и scrollInterpretation");
}
val action = if (direction) {
Node.ACTION_SCROLL_FORWARD
} else {
Node.ACTION_SCROLL_BACKWARD;
}
node.performAction(action);
return null;
}
