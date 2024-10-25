package kz.aisuluait.compositor;
import android.content.Context;
import kz.aisuluait.R;
import kz.altairait.getRole;
import kz.aisuluait.a11yevents.NodeCompat;
fun getText(node: NodeCompat, cxt: Context, isChild: Boolean = false, userLabel: String? = null): String {
val className = node.className ?: "";
val opensPopUpWindow = if (node.canOpenPopup()) cxt.getString(R.string.opens_pop_up_window) else "";
val collapseState = getCollapseState(node);
val collapse = if (collapseState is Int) cxt.getString(collapseState) else "";
val contentDescription = if (node.contentDescription?.isEmpty() == false) {
node.contentDescription;
} else if (node.text?.isEmpty() == false) {
node.text;
} else { ""; }
val text = if (!contentDescription.isEmpty()) {
"${contentDescription.trim()}\n";
} else { "" }
val getRole = getRole(node, cxt);
val roleDescription = if ((node.childCount > 0
&& node.roleDescription != null)
|| !isChild) {
"\n${if (node.roleDescription?.isEmpty() == false) node.roleDescription else getRole}\n";
} else {
"";
}
val isCheckable = node.isCheckable;
val isEnabled = if (!node.isEnabled
&& !isChild) {
"\n${cxt.getString(R.string.state_disable)}";
} else {
"";
}
val state = node.stateDescription?.trim()
val stateDescription = if (state?.isEmpty() == false) {
"${node.stateDescription}.";
} else {
if (className == "android.widget.Switch") {
if (node.isChecked) {
cxt.getString(R.string.state_enabled);
} else {
cxt.getString(R.string.state_off);
}
} else if (className == "android.widget.CheckBox") {
if (node.isChecked) {
cxt.getString(R.string.state_checked);
} else {
cxt.getString(R.string.state_not_checked);
}
} else {
"";
}
}
val isSelected = if ((node.isSelected
&& stateDescription == "")
&& !isChild) {
"${cxt.getString(R.string.state_selected)}.";
} else {
"";
}
//val textToReturn = stateDescription + " " + (contentDescription ?: text).toString() + "  " + isSelected + "  " + isEnabled + "  " + roleDescription;
val hintText = if (node.hintText != null
&& node.text != node.hintText) {
"\n${node.hintText}";
} else {
"";
}
val textToReturn = ("$collapse\n $stateDescription $isSelected ${userLabel ?: text} $hintText $isEnabled $opensPopUpWindow\n $roleDescription").trim();
if (!textToReturn.isEmpty()) {
return "$textToReturn\n";
}
return "";
}
