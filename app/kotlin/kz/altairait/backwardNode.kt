package kz.altairait
import android.os.Bundle;

import kz.aisuluait.a11yevents.Node
import kz.aisuluait.a11yevents.ActionInfo;
import kz.aisuluait.focus.*;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.R;
import kz.aisuluait.feedback.play;

// Переход к предыдущему узлу

fun backwardNode(node: Node, isChild: Boolean = false, childForComparison: Node? = null, callCount: Int = 0, callFromScrollInterpreter: Boolean = false): Node? {
if (isHtmlElement(node)) {
return forwardHtmlElement(node);
}
try {
var foundFocused = node.isAccessibilityFocused || isChild;
val checkerP = checkNotFocusableNode(node);
val parentIsCollection = node.collectionInfo != null;
var parentIsScrollable = node.isScrollable || isScrollable(node);
val parentActions = node.actionList ?: mutableListOf<ActionInfo?>();
val parentClassName = node.className ?: "";
val parentIsPager = (
parentClassName.indexOf("android") == 0
&& parentClassName.indexOf("ViewPager") != -1
);
val parentIsScrollableToPosition = parentActions.indexOf(
ActionInfo.ACTION_SCROLL_TO_POSITION
) != -1;
for (childId in node.childCount - 1 downTo 0) {
//node.refresh();
val child = node.getChild(childId) ?: return null;
if (child.isAccessibilityFocused
|| child.equals(childForComparison)) {
foundFocused = true;
continue;
}
if (foundFocused && !child.isVisibleToUser
&& child.childCount > 0) {
backwardNode(child, true)?.let {
return it;
}
}
if (foundFocused
&& (child.isVisibleToUser
|| child.performAction(ActionInfo.ACTION_SHOW_ON_SCREEN.id) == true
|| parentIsScrollableToPosition
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
&& child.refresh()
&& !child.isVisibleToUser) {
continue;
}
if (isScrollable(child)) {
if (child.childCount > 0) {
backwardNode(child, true)?.let {
return it;
}
continue;
}
}
val checker = checkNotFocusableNode(child);
val hasAnyClick = hasAnyClick(child);
val hasAnyFocus = hasAnyFocus(child);
val parent = getFocusableParent(child);
val checkLabels = checkLabels(child);
val isScrollViewItem = isScrollViewItem(child);
val isCollectionItem = isCollectionItem(child);
val fb = child.traversalBefore ?: child.traversalAfter;
if (fb != null) {
//return child;
}
if (isCollectionItem || isScrollViewItem) {
if (child.childCount > 0) {
backwardNode(child, true)?.let {
return it;
}
if (!checkLabels && !checker) {
continue;
}
return child;
} else {
if (hasAnyFocus || hasAnyClick
|| checkLabels) {
return child;
}
}
continue;
}
if (child.childCount == 0) {
if (hasAnyFocus || hasAnyClick
 || checkLabels && parent == null) {
return child;
}
continue;
}
if (isCollection(child)) {
backwardNode(child, true)?.let {
return it;
}
continue;
}
if (child.childCount > 0 && checker) {
backwardNode(child, true)?.let {
return it;
}

if (checkLabels && parent == null
|| hasAnyClick || hasAnyFocus
|| parentIsScrollable || parentIsCollection) {
return child;
}
}
if (child.childCount > 0 && !checker) {
backwardNode(child, true)?.let {
return it;
}
if (checkLabels && parent == null) {
return child;
}
}
}
}
if (!node.isAccessibilityFocused && node.isVisibleToUser) {
val checkLabels = checkLabels(node);
val hasAnyClick = hasAnyClick(node);
val hasAnyFocus = hasAnyFocus(node);
val isCollectionItem = isCollectionItem(node);
val isScrollViewItem = isScrollViewItem(node);
val parent = getFocusableParent(node);
if (parentIsScrollable || parentIsCollection
|| node.childCount > 0 && !checkerP && !checkLabels) {
throw Exception();
}
if (hasAnyClick || hasAnyFocus
|| isCollectionItem || isScrollViewItem
|| checkLabels && parent == null) {
return node;
}
}
if (node.parent == null
&& (checkLabels(node) || hasAnyClick(node))) {
return node;
}
if (!parentIsPager && parentIsScrollable) {
scrollInterpretation(node, false);
}
}
catch (e: Exception) {
e.printStackTrace();
}
// Нам не нужно переберать родительские узлы если функция вызвана как дочерняя
// isChild == true
if (isChild) {
return null;
}
return backwardNode(node.parent ?: run {
// Воспроизводим сигнал
val endSound = Global.prefs.getString("end_sound", null) ?: R.raw.end;
play(endSound);
return null;
}, false, node);
}
