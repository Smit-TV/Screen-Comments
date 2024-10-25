package kz.aisuluait.compositor;
import android.content.Context;
import kz.aisuluait.R;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.NodeCompat;
import kz.aisuluait.a11yevents.Action;
import kz.aisuluait.focus.checkLabels;
/* *
* @param (NodeCompat): node - Основной узел для получения текста.
* @param (Node): nodeInfo - Узел являющийся @node  
* @param (Boolean): isSecondCall - Если текст отсутствует то будем собирать все текста из узла.
* @returns (String): текст 
*/
fun parseNode(node: NodeCompat,
nodeInfo: Node,
cxt: Context,
isChild: Boolean = false,
isSecondCall: Boolean = false): String? {
try {
var textToReturn = (node.contentDescription ?: "").toString();
if (textToReturn != "") {
return textToReturn;
}
val childs = node.childCount;
for (childId in 0 until childs) {
val child = node.getChild(childId);
val childInfo = nodeInfo.getChild(childId);
val checkLabels = checkLabels(childInfo);
if (isSecondCall
&& isReadable(child)) {
if (child.childCount == 0) {
textToReturn = "$textToReturn ${getText(child, cxt, true).trim()}";
} else {
textToReturn = "$textToReturn ${parseNode(child, childInfo, cxt, true, true) ?: ""}";
}
continue;
}
if (child.isVisibleToUser) {
val actions = (
!child.isClickable
&& !child.isLongClickable
&& !child.isFocusable
&& !child.isScreenReaderFocusable
&& child.collectionInfo == null
&& child.collectionItemInfo == null
&& child.rangeInfo == null);
if ((checkLabels
&& actions
&& child.rangeInfo == null)
|| child.stateDescription != null && !child.isClickable
&& child.rangeInfo == null) {
textToReturn = "$textToReturn ${getText(child, cxt, true).trim()}";
} else  if (child.childCount > 0
&& actions
&& child.rangeInfo == null
&& !checkLabels) {
textToReturn = "$textToReturn ${parseNode(child, childInfo, cxt, true) ?: ""}";
}
}
}
val nodeText = NodeProperties(node);
val finalText =
("${nodeText.getCollapseState()}\n${nodeText.getOpensPopUpWindow()}\n${nodeText.checkSelect()} ${nodeText.getState()}\n$textToReturn\n${nodeText.getText()}\n${nodeText.getHint()}\n${nodeText.checkState()}\n${nodeText.getRoleName()}").trim();
if (finalText.isEmpty()) {
if (!isChild && !isSecondCall) {
return parseNode(node, nodeInfo, cxt, false, true);
}
return null;
} else {
return finalText;
}
}
catch (e: Exception) {
return "";
}
}
