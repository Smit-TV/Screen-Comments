package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
// Является ли узел элементом ScrollView
fun isScrollViewItem(node: Node): Boolean {
if (isScrollable(node.parent ?: return false)) {
return true;
}
return false;
}
