package kz.aisuluait.functions;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.farwardNode;
import kz.altairait.backwardNode;
import kz.altairait.utils.getUniqueId;
// backward -> false.
// farward -> true
fun nodeByDirection(direction: Boolean): Node? {
val node = Global.node ?: return null;
node?.refresh();
node?.recycle();
if (direction) {
if (node.childCount > 0) {
return farwardNode(node);
} else {
val parent = node.parent ?: return null;
return farwardNode(parent);
}
} else {
if (node.childCount > 0) {
return backwardNode(node);
} else {
return backwardNode(node.parent ?: return null);
}
}
}

