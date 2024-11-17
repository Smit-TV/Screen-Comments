package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.log.getLog;
import android.util.Log;

// Обрабатывает прокрутку при переходе к следующему и предыдущему узлу
// @param direction
// true -> SCROLL_FORWARD
/* *
* Прокрутке не подлежит ViewPager
* Мы также не должны прокручивать View не являющиеся стандартными ListView, RecyclerView и т.п.
*/
// false -> SCROLL_BACKWARD
fun scrollInterpretation(node: Node, direction: Boolean): Node? {
//return null
val action = if (direction) {
ActionInfo.ACTION_SCROLL_DOWN.id;
} else {
Node.ACTION_SCROLL_BACKWARD;
}
//Global.serviceInterface.clearCachedSubtree(node);
node.performAction(action);
//Thread.sleep(100);
Global.serviceInterface.clearCachedSubtree(node);
//Thread.sleep(17);
val newNode = Node.obtain(node);
farwardNode(newNode, false, Global.node, 0, true)?.let {
return it;
}
//Global.scrollInitByGesture = true;
return null;
}
