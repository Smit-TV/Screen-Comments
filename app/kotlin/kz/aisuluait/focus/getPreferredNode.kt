package kz.aisuluait.focus;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Window;
fun getPreferredNode(node: Node): Node? {
return getFocusableNode(node);
val window = node.window;
val windowRoot = window.root;
val checkLabels = checkLabels(node);
val checker = checkNotFocusableNode(node);
if (node.className ?: "" == "android.webkit.WebView"
|| !node.isVisibleToUser) {
return null;
}
node.collectionItemInfo?.let {
if (checker
|| checkLabels) {
return node;
} else {
return null;
}
}

val hasAnyClick = hasAnyClick(node);
val hasAnyFocus = hasAnyFocus(node);
if ((node.equals(windowRoot)
|| node.parent == null
|| node.collectionInfo != null)
&& !checkLabels
|| node.collectionInfo != null) {
return null; 
}
val parent = getFocusableParent(node);
val isScrollable = isScrollable(node);
if (isScrollable
&& !checkLabels) {
return parent;
}
if (node.childCount > 0) {
if (hasAnyFocus
&& checker) {
return node;
} else if (isScrollable
&& checkLabels) {
return node;
}
val possibleParent = node.parent;
if ((possibleParent?.collectionInfo != null
|| isScrollable(possibleParent))
&& (checker || checkLabels)) {
return node;
} else if (checkLabels
&& parent == null) {
return node;
} else if (checker
&& hasAnyClick) {
return node;
} else if (checker
&& checkLabels
&& parent == null) {
return node;
}
} else {
if (hasAnyClick(node)
|| hasAnyFocus
|| checkLabels && parent == null) {
return node;
}
}
return parent;
}
