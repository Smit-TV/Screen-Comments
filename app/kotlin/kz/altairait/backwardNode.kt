package kz.altairait
import android.os.Bundle;

import kz.aisuluait.a11yevents.Node
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.focus.*;

fun backwardNode(node: Node, isChild: Boolean = false, childForComparison: Node? = null): Node? {
var foundFocused = node.isAccessibilityFocused || isChild;
val checkerP = checkNotFocusableNode(node);
val parentIsCollection = node.collectionInfo != null;
val parentIsScrollable = node.isScrollable || isScrollable(node);
val parentActions = node.actionList;
val parentClassName = node.className ?: "";
val parentIsPager = (
parentClassName.indexOf("android") == 0
&& parentClassName.indexOf("ViewPager") != -1
);
for (childId in  node.childCount - 1 downTo 0) {
val child = node.getChild(childId) ?: return null;
if (child.isAccessibilityFocused
|| child.equals(childForComparison)) {
foundFocused = true;
continue;
}
if (foundFocused
&& (child.isVisibleToUser
|| parentIsCollection
|| child.collectionItemInfo != null
|| child.performAction(
ActionInfo.ACTION_SHOW_ON_SCREEN.id
)
)) {
val childCollectionItem = child.collectionItemInfo;
var args = Bundle();
if (parentActions.indexOf(
ActionInfo.ACTION_SCROLL_TO_POSITION
) != -1
&& childCollectionItem != null) {
args.apply {
putInt(
Node.ACTION_ARGUMENT_ROW_INT, childCollectionItem.rowIndex
);
putInt(
Node.ACTION_ARGUMENT_COLUMN_INT, childCollectionItem.columnIndex
);
}
}
if (!node.performAction(
ActionInfo.ACTION_SCROLL_TO_POSITION.id, args
)
/*&& !parentIsPager
&& !node.performAction(
ActionInfo.ACTION_SCROLL_FORWARD.id
)*/
&& child.refresh()
&& !child.isVisibleToUser) {
continue;
}
if (isScrollable(child)) {
backwardNode(child, true)?.let {
return it;
}
continue;
}
val checker = checkNotFocusableNode(child);
val hasAnyClick = hasAnyClick(child);
val hasAnyFocus = hasAnyFocus(child);
val parent = getFocusableParent(child);
val checkLabels = checkLabels(child);
if (isHtmlElement(child)) {
if (child.isCheckable || hasAnyClick //|| hasAnyFocus 
|| checkLabels) {
return child;
}
continue;
}

val fb = child.traversalBefore ?: child.traversalAfter;
if (fb != null) {
//return child;
}
if (child.collectionItemInfo != null) {
if (!child.isVisibleToUser) {
val result = child.performAction(
ActionInfo.ACTION_SHOW_ON_SCREEN.id
);
if (!result) {
continue;
}
}
if (checkLabels
|| (hasAnyClick
|| hasAnyFocus) && checker) {
return child;
} else if (child.childCount > 0) {
backwardNode(child, true)?.let {
return it;
}
} else { continue; }
}
if (child.childCount == 0) {
if (child.stateDescription != null
&& hasAnyFocus == null) {
continue;
}
if (hasAnyFocus || hasAnyClick
 || checkLabels && parent == null) {
return child;
}
}
if (child.collectionInfo != null) {
val focusTo = backwardNode(child, true);
focusTo?.let {
return focusTo;
}
}
if (child.childCount > 0 && checker) {
if (checkLabels && parent == null
|| hasAnyClick || hasAnyFocus
|| isScrollable(child.parent) || child.parent?.collectionInfo != null) {
return child;
}
val getChild = backwardNode(child, true);
getChild?.let {
return it;
}
}
if (child.childCount > 0 && !checker) {
if (checkLabels && parent == null) {
return child;
}
return backwardNode(child, true);
}
if (childId + 1 > node.childCount) {
//return backwardNode(node.parent ?: return null, true);
}
}
}
return backwardNode(node.parent ?: return null, false, node);
}
