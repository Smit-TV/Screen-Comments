package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
fun checkLabels(node: Node): Boolean {
try {
if (node.contentDescription?.isNotEmpty() == true
|| node.hintText?.isNotEmpty() == true
|| node.stateDescription?.isNotEmpty() == true
|| node.text?.isNotEmpty() == true) {
return true;
}
}
catch (e: Exception) {}
return false;
}
