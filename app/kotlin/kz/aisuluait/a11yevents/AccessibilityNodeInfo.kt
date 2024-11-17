package kz.aisuluait.a11yevents;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.RangeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.TouchDelegateInfoCompat;
import android.graphics.Rect;
typealias Node = AccessibilityNodeInfo;
typealias NodeCompat = AccessibilityNodeInfoCompat;
typealias CollectionInfo = CollectionInfoCompat;
typealias RangeInfo = RangeInfoCompat;
typealias Action = AccessibilityActionCompat;
typealias CollectionItemInfo = CollectionItemInfoCompat;
typealias TouchDelegateInfo = TouchDelegateInfoCompat;
typealias Range = RangeInfo;
typealias ActionInfo = AccessibilityAction;
typealias Collection = CollectionInfo;
typealias CollectionItem = CollectionItemInfo;

// Класс с полезными методами
class NodeUtils(private val node: Node) {
// Замена Node#findFocus. Из-за ограничений
// https://developer.android.com/reference/android/view/accessibility/AccessibilityNodeInfo#findAccessibilityNodeInfosByText(java.lang.String)
// Значения focusType Node#FOCUS_ACCESSIBILITY и Node#FOCUS_INPUT
fun findFocus(focusType: Int): Node? {
try {
return node.findFocus(focusType) ?: run {
for (i in 0 until node.childCount) {
val child = node.getChild(i) ?: continue;
if (focusType == Node.FOCUS_ACCESSIBILITY && child.isAccessibilityFocused
|| focusType == Node.FOCUS_INPUT && node.isFocused) {
return child;
}
if (child.childCount > 0) {
val focusedNode = NodeUtils(child).findFocus(focusType) ?: continue;
return focusedNode;
}
}
return null;
}
}
catch (e: Exception) {
e.printStackTrace();
}
return null;
}
}
