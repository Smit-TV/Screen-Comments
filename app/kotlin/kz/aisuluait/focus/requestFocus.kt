package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.ActionInfo;
fun requestFocus(node: Node, hasAParent: Boolean, checker: Boolean): Boolean {
val className = (node.className ?: "").toString();

if (className == "android.webkit.WebView") {
return false;
}
val checkLabels = checkLabels(node);
node.collectionInfo?.let {
return false;
}
val hasAnyClick = hasAnyClick(node);
if (isScrollable(node)
&& (checkLabels
|| hasAnyClick && checker
|| node.isScreenReaderFocusable
|| node.isDismissable
|| node.canOpenPopup())) {
return true;
}
val hasAnyFocus = hasAnyFocus(node);
if ((isScrollable(node)
&& !checkLabels
&& !hasAnyClick)) {
return false;
}

node.collectionItemInfo?.let {
if (hasAnyClick
//|| hasAnyFocus
|| checkLabels
|| checker) {
return true;
}
}

if (node.childCount == 0) {
if (hasAnyClick
|| node.isFocusable && checkLabels
|| node.isDismissable
|| node.isScreenReaderFocusable) {
return true;
} else if (hasAParent == false
&& checkLabels) {
return true;
}
} else {
if (!checker
&& !checkLabels) {
return false;
} else if (checkLabels
&& hasAParent == false) {
return true;
} else if ((node.isClickable
|| node.isLongClickable
|| node.isFocusable
|| node.isDismissable
|| !node.isEnabled
|| node.isScreenReaderFocusable)
&& (checker
|| checkLabels)) {
return true;
}
}
return false;
}
