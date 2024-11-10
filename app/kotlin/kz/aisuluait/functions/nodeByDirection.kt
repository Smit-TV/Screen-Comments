package kz.aisuluait.functions;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.farwardNode;
import kz.altairait.backwardNode;
import kz.altairait.utils.getUniqueId;
// backward -> false.
// farward -> true
fun nodeByDirection(direction: Boolean): Node? {
try {
val node = Global.getA11yFocusedNode() ?: return null;
//node.refresh();
//node?.recycle();
if (direction) {
if (node.childCount > 0) {
return farwardNode(node);
} else {
val parent = node.parent ?: return null;
return farwardNode(parent);
}
} else {
val parent = node.parent ?: return null;
if (node.childCount > 0) {
return backwardNode(parent);
} else {
return backwardNode(parent ?: return null);
}
}
}
catch (e: Exception) {}
return null;
}

