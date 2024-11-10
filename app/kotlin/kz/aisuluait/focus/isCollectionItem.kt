package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
// Является ли узел элементом коллекции
fun isCollectionItem(node: Node): Boolean {
if (node.collectionItemInfo != null
|| isCollection(node.parent ?: return false)) {
return true;
}
return false;
}