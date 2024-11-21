package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.focus.isScrollable;
import kz.aisuluait.functions.devMenu;
fun scrollInterpretation(nodeInfo: Node, direction: Boolean): Node? {
//return null
if (!isScrollable(nodeInfo)) {
throw IllegalArgumentException("Узел не является прокручиваемым. Ошибка в farwardNode и scrollInterpretation");
}
val action = if (direction) {
NodeCompat.ACTION_SCROLL_FORWARD
} else {
NodeCompat.ACTION_SCROLL_BACKWARD;
}
/*node?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS);*/
//node.wait();
val lastNode = Global.getA11yFocusedNode();
val node = NodeCompat.wrap(nodeInfo);
node.performAction(action);
Thread.sleep(200);
node.refresh();
/*Thread.sleep(250);
node.refresh();*/
farwardNode(node.unwrap(), true, lastNode, 0, true)?.let {
return it;
}
//devMenu(lastNode, "refresh");
return null;
}
