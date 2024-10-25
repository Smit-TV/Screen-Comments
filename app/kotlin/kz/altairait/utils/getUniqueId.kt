package kz.altairait.utils;
import kz.aisuluait.a11yevents.Node;
fun getUniqueId(node: Node): String {
return "${node.text}${node.childCount}${node.uniqueId}${node.viewIdResourceName}${node.className}${node.packageName}";
}