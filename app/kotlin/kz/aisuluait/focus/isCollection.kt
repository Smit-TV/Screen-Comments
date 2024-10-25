package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
// Для тех view которые симантически являются списками или сетками но не явные
fun isCollection(node: Node): Boolean {
val classNameStr = (node.className ?: "").toString();
return (node.collectionInfo != null
|| classNameStr.indexOf("android") == 0
&& (
classNameStr.indexOf("ListView") != -1
|| classNameStr.indexOf("GridView") != -1
|| classNameStr.indexOf("RecyclerView") != -1)
);
}
