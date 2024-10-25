package kz.altairait;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.focus.isCollection;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.Global;
fun getRole(node: NodeCompat, cxt: Context = Global.cxt): String {
val parent = node.parent;
var role: Any = "";
val className = (node.className ?: "").toString();
if (className == "android.webkit.WebView") {
role = R.string.role_webview;
} else if (node.roleDescription?.isEmpty() == false) {
return node.roleDescription.toString();
} else if (node.isHeading) {
role = R.string.role_title;
} else if (className == "android.widget.ImageView"
&& !node.isClickable
&& !node.isLongClickable
&& !node.isContextClickable) {
role = R.string.role_image;
} else if ((className == "android.widget.Button"
|| className == "android.widget.ImageButton"
|| className == "android.widget.ImageView")
&& (node.isClickable
|| node.isLongClickable
|| node.isContextClickable)) {
role = R.string.role_button;
} else if (className == "android.widget.ToggleButton"
|| className == "android.widget.Switch"
|| className == "android.widget.RadioButton") {
role = R.string.role_switch;
} else if (className == "android.widget.SeekBar") {
role = R.string.role_slider;
} else if (className == "android.widget.EditText") {
if (!node.isPassword) {
role = R.string.role_editable;
} else {
role = R.string.role_password;
}
} else if (className == "android.widget.ProgressBar") {
role = R.string.role_progress;
} else if (node.collectionItemInfo != null
|| parent != null
&& isCollection(parent.unwrap())) {
if (parent != null
&& parent.collectionInfo?.columnCount == 1) {
role = R.string.role_list_item;
}
}
if (node.collectionInfo != null) {
if (node.collectionInfo.columnCount == 1) {
role = R.string.role_list;
} else {
role = R.string.role_table;
}
}
if (role is String) {
return role;
} 
if (role is Int) {
return cxt.getString(role);
}
return "";
}
