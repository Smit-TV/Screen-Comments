package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
fun checkLabels(node: Node): Boolean {
if (node.text?.isBlank() != false
&& node.contentDescription?.isBlank() != false
&& node.hintText?.isBlank() != false
&& node.stateDescription?.isBlank() != false) {
return false;
}
return true;
}
