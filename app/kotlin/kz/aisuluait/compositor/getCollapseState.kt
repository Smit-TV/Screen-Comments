package kz.aisuluait.compositor;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.ActionInfo;
fun getCollapseState(node: NodeCompat): Int? {
for (action in node.actionList) {
if (action.id == ActionInfo.ACTION_COLLAPSE.id) {
return R.string.collapsed;
} else if (action.id == ActionInfo.ACTION_EXPAND.id) {
return R.string.expanded;
}
}
return null;
}
