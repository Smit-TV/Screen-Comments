package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
import android.util.Log;
fun isScrollable(node: Node?): Boolean {
node ?: return false;
val className = node.className ?: "";
if (node.isScrollable
|| className.indexOf("android") == 0
&& (className.indexOf("ScrollView") != -1
|| className.indexOf("ViewPager") != -1)) {
return true;
}
val actions = node.actionList ?: mutableListOf<ActionInfo?>();
// У некоторых реализаций seekbar нет действия установки прогресса
if (actions.indexOf(
ActionInfo.ACTION_SET_PROGRESS
) != -1
|| node.childCount == 0) {
return false;
}
if (actions.indexOf(
ActionInfo.ACTION_SCROLL_FORWARD
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_BACKWARD
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_LEFT
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_UP
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_DOWN
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_RIGHT
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_TO_POSITION
) != -1
|| actions.indexOf(
ActionInfo.ACTION_SCROLL_IN_DIRECTION
) != -1) {
return true;
}
return false;
}
