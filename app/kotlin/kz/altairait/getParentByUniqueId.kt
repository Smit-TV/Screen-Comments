package kz.altairait;
import kz.aisuluait.a11yevents.Node;
import kz.altairait.utils.getUniqueId;
fun getParentByUniqueId(node: Node, uniqueId: String): Node? {
try {
val parent = node.parent ?: return null;
if (uniqueId == getUniqueId(parent)) {
return parent;
}
return getParentByUniqueId(parent, uniqueId);
}
catch (e: IllegalStateException) {
return null;
}
}
