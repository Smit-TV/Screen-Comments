package kz.aisuluait.log;
import kz.aisuluait.a11yevents.Node;
fun takeXmlLayout(node: Node): String {
val rootClassName = getClassName(node);
var xmlLayout = "<$rootClassName>";
for (childId in 0 until node.childCount) {
val child = node.getChild(childId);
val childParams = getNodeParams(node);
xmlLayout = "$xmlLayout\n <$childParams>";
}
return xmlLayout;
}
private fun getNodeParams(node: Node): String {
val className = getClassName(node);
val isSoloTag = if (node.childCount < 1) true else false;
val internal = if (isSoloTag) ">\n${takeXmlLayout(node)}" else "/>";
val ct = (node.contentDescription ?: node.text).toString();
val params = "<$className$internal\n$ct\n";
return params;
}
private fun getClassName(node: Node): String {
return (node.className ?: "").toString();
}
