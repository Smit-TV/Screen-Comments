package kz.aisuluait.compositor;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.Global;
import kz.altairait.getParam;
fun compositor(nodeInfo: Node, cxt: Context): String {
//return ""
nodeInfo.refresh();
val node = NodeCompat.wrap(nodeInfo);
var returnText: String? = null;
if (node.contentDescription != null
|| node.text != null) {
returnText = NodeProperties(node, cxt).getText();
} else if (node.childCount > 0) {
returnText = parseNode(node, nodeInfo, cxt);
} else {
if (returnText?.isEmpty() != false) {
returnText = Global.prefs.getString("label_${node.packageName}_${node.viewIdResourceName ?: ""}", null);
if (returnText?.isEmpty() != false) {
returnText = cxt.getString(R.string.no_label);
}
returnText = NodeProperties(node, cxt, false, returnText).getText();
}
}

return returnText?.trim() ?: cxt.getString(R.string.no_label);
}
