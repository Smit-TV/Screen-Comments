package kz.aisuluait.functions;
import kz.aisuluait.a11yevents.Node;
import kz.aisuluait.a11yevents.Global;
import kz.aisuluait.a11yevents.Window;
import kz.altairait.farwardNode;
import kz.altairait.backwardNode;

// Перейдите к следующему окну
fun nextWindow(isChild: Boolean = false) {
val currentWindow = (Global.node ?: Global.getA11yFocusedNode())?.window;
if (currentWindow != null) {
for (windowIndex in 0 until currentWindow.childCount) {
val child = currentWindow.getChild(windowIndex);
if (child.isAccessibilityFocused) {
continue;
}
val root = child.root ?: continue;
val result = farwardNode(root, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS) ?: false;
if (result) {
return;
}
}
}
val windows = Global.serviceInterface.getWindowsList();
var foundFocused = false || isChild;
for (a in 0 until windows.size) {
val w = windows[a];
if (w.isAccessibilityFocused) {
foundFocused = true;
continue;
}
if (foundFocused) {
val root = w.root ?: continue;
val result = farwardNode(root, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS) ?: false;
if (result) {
return;
}
}
}
if (isChild) {
return;
}
nextWindow(true);
}

// Переход к предыдущему окну
fun previousWindow(isChild: Boolean = false) {
val currentWindow = (Global.node ?: Global.getA11yFocusedNode())?.window;
if (currentWindow != null) {
for (windowIndex in currentWindow.childCount - 1 downTo 0) {
val child = currentWindow.getChild(windowIndex);
if (child.isAccessibilityFocused) {
continue;
}
val root = child.root ?: continue;
val result = backwardNode(root, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS) ?: false;
if (result) {
return;
}
}
}
val windows = Global.serviceInterface.getWindowsList();
var foundFocused = false || isChild;
for (a in windows.size - 1 downTo 0) {
val w = windows[a];
if (w.isAccessibilityFocused) {
foundFocused = true;
continue;
}
if (foundFocused) {
val root = w.root ?: continue;
val result = backwardNode(root, true)?.performAction(
Node.ACTION_ACCESSIBILITY_FOCUS) ?: false;
if (result) {
return;
}
}
}
if (isChild) {
return;
}
previousWindow(true);
}
