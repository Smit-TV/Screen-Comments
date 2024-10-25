package kz.aisuluait.compositor;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.altairait.getRole;
import kz.aisuluait.a11yevents.Global;
class NodeProperties(
private val node: NodeCompat) {
private val cxt = Global.cxt;
private val opensPopUpWindow = if (node.canOpenPopup()) cxt.getString(R.string.opens_pop_up_window) else "";
private val collapseState = getCollapseState(node);
private val collapse = if (collapseState is Int) cxt.getString(collapseState) else "";
private val isDisabled = if (!node.isEnabled) {
cxt.getString(R.string.state_disable);
} else { ""; }
private val stateDescription = node.stateDescription ?: "";
private val role = node.roleDescription ?: getRole(node);
private val hintText = node.hintText ?: "";
private val text = node.contentDescription ?: node.text ?: "";
private val isSelected = if (stateDescription == ""
&& node.isSelected) {
cxt.getString(R.string.state_selected);
} else { ""; }
fun getText(): String {
return text.toString();
}
fun getRoleName(): String {
return role.toString();
}
fun getState(): String {
return stateDescription.toString();
}
fun checkState(): String {
return isDisabled.toString();
}
fun getHint(): String {
if (hintText != text) {
return hintText.toString();
}
return "";
}
fun checkSelect(): String {
return isSelected;
}
fun getCollapseState(): String {
return collapse;
}
fun getOpensPopUpWindow(): String {
return opensPopUpWindow;
}
}
